package woowacourse.kanban.board.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.kanban.board.domain.CardData
import woowacourse.kanban.board.domain.CardManagerState
import woowacourse.kanban.board.domain.CardTaskState
import woowacourse.kanban.board.ui.theme.KanbanCardColor.DefaultBackground
import woowacourse.kanban.board.ui.theme.KanbanCardColor.DefaultContent
import woowacourse.kanban.board.ui.theme.KanbanCardColor.SelectedBackground
import woowacourse.kanban.board.ui.theme.KanbanCardColor.SelectedContent

@Composable
fun CardCreationPanel(
    modifier: Modifier = Modifier,
    onAddItem: (CardData) -> Unit,
    onShowCardCreationPanel: (Boolean) -> Unit,
) {
    var taskTitle by remember { mutableStateOf("") }
    var contents by remember { mutableStateOf("") }
    var tempTags by remember { mutableStateOf("") }
    val tags = CardData.parseTag(tempTags)
    var state by remember { mutableStateOf(CardTaskState.TODO) }
    var manager by remember { mutableStateOf(CardManagerState.DINO) }
    var tagInfoText by remember { mutableStateOf("5자 이내의 태그를 최대 5개까지 등록할 수 있습니다.") }
    val createEnabled by remember {
        derivedStateOf {
            CardData.isValidText(taskTitle) && CardData.isValidTag(tempTags)
        }
    }

    OutlinedCard(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.background(DefaultBackground).width(672.dp),
        ) {
            CardCreationPanelHeaderSection(
                onShowCardCreationPanel = onShowCardCreationPanel,
            )

            HorizontalDivider(modifier = Modifier.fillMaxWidth())

            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                CardCreationPanelSection(
                    title = "제목 *",
                    placeholder = "태스크 제목을 입력하세요",
                    value = taskTitle,
                    onTextChange = {
                        taskTitle = it
                    },
                    showAdditionalInfo = !CardData.isValidText(taskTitle),
                    infoText = CardData.getTitleInfo(),
                    isError = !CardData.isValidText(taskTitle),
                )

                CardCreationPanelSection(
                    title = "설명",
                    placeholder = "태스크에 대한 자세한 설명을 입력하세요",
                    value = contents,
                    onTextChange = { contents = it },
                )

                CardCreationPanelSection(
                    title = "태그",
                    placeholder = "태그를 쉼표로 구분하여 입력하세요 (예: 버그, 긴급)",
                    value = tempTags,
                    onTextChange = {
                        tempTags = it
                        tagInfoText = CardData.isValidTagInfo(tempTags)
                    },
                    showAdditionalInfo = true,
                    infoText = tagInfoText,
                    isError = !CardData.isValidTag(tempTags),
                )

                CardCreationPanelStateSection(
                    selectedState = state,
                    onStateChange = { state = it },
                )

                CardCreationPanelManagerSection(
                    selectedManager = manager,
                    onManagerChange = { manager = it },
                )

                HorizontalDivider(modifier = Modifier.fillMaxWidth())

                ActionButtonSection(
                    createEnabled = createEnabled,
                    onClick = { onShowCardCreationPanel(false) },
                    onCreate = {
                        onAddItem(
                            CardData.create(
                                taskTitle,
                                contents,
                                tags,
                                manager,
                                state,
                            ),
                        )
                    },
                )
            }
        }
    }
}

@Composable
private fun CardCreationPanelHeaderSection(
    onShowCardCreationPanel: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 28.dp, horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "새 태스크 생성",
            fontSize = 20.sp,
            fontWeight = FontWeight.W600,
            lineHeight = 28.sp,
            letterSpacing = (-0.45).sp,
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "닫기 아이콘",
            modifier = Modifier.clickable { onShowCardCreationPanel(false) },
        )
    }
}

@Composable
private fun CardCreationPanelSection(
    title: String,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    value: String,
    onTextChange: (String) -> Unit = {},
    showAdditionalInfo: Boolean = false,
    infoText: String = "",
    isError: Boolean = false,
) {
    val errorColor = Color(0xFFB3261E)
    Column(
        modifier = modifier,
    ) {
        TitleText(title)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = { onTextChange(it) },
            trailingIcon = {
                if (isError) Icon(
                    imageVector = Icons.Default.Error,
                    contentDescription = "에러 아이콘",
                    tint = errorColor,
                )
            },
            isError = isError,
            placeholder = {
                Text(
                    text = placeholder,
                    color = Color(0xFFAAAAAA),
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    letterSpacing = 1.sp,
                )
            },
            textStyle = TextStyle(
                color = if (isError) errorColor else DefaultContent,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 1.sp,
            ),
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        )
        if (showAdditionalInfo) {
            Text(
                text = infoText,
                color = if (isError) errorColor else Color(0xFF49454F),
                fontSize = 12.sp,
                fontWeight = FontWeight.W400,
                lineHeight = 16.sp,
                modifier = Modifier.padding(top = 4.dp),
            )
        }
    }
}


@Composable
private fun CardCreationPanelStateSection(
    selectedState: CardTaskState,
    onStateChange: (CardTaskState) -> Unit,
) {
    Column {
        TitleText("상태 *")
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CardTaskState.entries.forEach { state ->
                StateButton(
                    text = state.taskState,
                    isSelected = selectedState == state,
                    onClick = { onStateChange(state) },
                    modifier = Modifier
                        .width(200.dp)
                        .height(52.dp),
                )
            }
        }
    }
}

@Composable
private fun StateButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val containerColor = if (isSelected) SelectedBackground else DefaultBackground
    val contentColor = if (isSelected) SelectedContent else DefaultContent
    val borderColor = if (isSelected) SelectedContent else SelectedBackground

    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(20),
        border = BorderStroke(1.dp, borderColor),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = containerColor,
            contentColor = contentColor,
        ),
        modifier = modifier,
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = (-0.3).sp,
            lineHeight = 24.sp,
        )
    }
}

@Composable
private fun CardCreationPanelManagerSection(
    selectedManager: CardManagerState,
    onManagerChange: (CardManagerState) -> Unit,
) {
    Column {
        TitleText("담당자 *")
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            CardManagerState.entries.forEach { manager ->
                ManagerButton(
                    text = manager.managerName,
                    isSelected = selectedManager == manager,
                    onClick = { onManagerChange(manager) },
                    modifier = Modifier
                        .width(200.dp)
                        .height(68.dp),
                )
            }
        }
    }
}

@Composable
private fun ManagerButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val containerColor = if (isSelected) SelectedBackground else DefaultBackground
    val contentColor = if (isSelected) SelectedContent else DefaultContent
    val borderColor = if (isSelected) SelectedContent else SelectedBackground

    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(20),
        border = BorderStroke(1.dp, borderColor),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = containerColor,
            contentColor = contentColor,
        ),
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "매니저 아이콘",
                modifier = Modifier.size(24.dp),
                tint = Color(0xFF838383),
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = text,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = (-0.15).sp,
                lineHeight = 20.sp,
            )
        }
    }
}

@Composable
private fun ActionButtonSection(
    createEnabled: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    onCreate: () -> Unit = {},
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ActionButton(
            buttonText = "취소",
            enabled = true,
            onClick = { onClick() },
        )
        Spacer(modifier = Modifier.width(12.dp))
        ActionButton(
            buttonText = "생성",
            enabled = createEnabled,
            onClick = {
                onClick()
                onCreate()
            },
        )
    }
}

@Composable
private fun ActionButton(
    buttonText: String,
    enabled: Boolean,
    onClick: () -> Unit = {},
) {
    val contentColor = if (buttonText == "생성") DefaultBackground else Color(0xFF364153)
    val buttonColor = if (buttonText == "생성") Color(0xFF4F39F6) else DefaultBackground
    val elevation = if (buttonText == "생성") 3.dp else 0.dp

    Button(
        onClick = { onClick() },
        enabled = enabled,
        modifier = Modifier,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = elevation,
            pressedElevation = elevation,
            disabledElevation = elevation,
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
            contentColor = contentColor,
        ),
        shape = RoundedCornerShape(20),
    ) {
        Text(
            text = buttonText,
            color = contentColor,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            letterSpacing = (-0.3).sp,
            lineHeight = 24.sp,
        )
    }
}

@Composable
private fun TitleText(
    title: String,
) {
    Text(
        text = title,
        fontSize = 14.sp,
        color = Color(0xFF364153),
        fontWeight = FontWeight.Medium,
        lineHeight = 20.sp,
        letterSpacing = 0.15.sp,
    )
}