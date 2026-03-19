package woowacourse.kanban.board.ui.Card.CardCreationScreen

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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.kanban.board.domain.Card.ActionButtonType
import woowacourse.kanban.board.domain.Card.Card
import woowacourse.kanban.board.domain.Card.CardManagerState
import woowacourse.kanban.board.domain.Card.CardTaskState
import woowacourse.kanban.board.ui.theme.KanbanCardColor.DefaultBackground
import woowacourse.kanban.board.ui.theme.KanbanCardColor.DefaultContent
import woowacourse.kanban.board.ui.theme.KanbanCardColor.SelectedBackground
import woowacourse.kanban.board.ui.theme.KanbanCardColor.SelectedContent

@Composable
fun CardCreationScreen(
    modifier: Modifier = Modifier,
    onAddItem: (Card) -> Unit,
    onShowCardCreationPanel: (Boolean) -> Unit,
) {
    var taskTitle by remember { mutableStateOf("") }
    var contents by remember { mutableStateOf("") }
    var tempTags by remember { mutableStateOf("") }
    val tags = Card.parseTag(tempTags)
    var state by remember { mutableStateOf(CardTaskState.TODO) }
    var manager by remember { mutableStateOf(CardManagerState.DINO) }
    var tagInfoText by remember { mutableStateOf("5자 이내의 태그를 최대 5개까지 등록할 수 있습니다.") }
    val createEnabled by remember {
        derivedStateOf {
            Card.isValidText(taskTitle) && Card.isValidTag(tempTags)
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
                CardCreationPanelFormSection(
                    title = "제목 *",
                    placeholder = "태스크 제목을 입력하세요",
                    value = taskTitle,
                    onTextChange = {
                        taskTitle = it
                    },
                    showAdditionalInfo = !Card.isValidText(taskTitle),
                    testTag = "titleTextField",
                    infoText = Card.getTitleInfo(),
                    isError = !Card.isValidText(taskTitle),
                )

                CardCreationPanelFormSection(
                    title = "설명",
                    placeholder = "태스크에 대한 자세한 설명을 입력하세요",
                    value = contents,
                    onTextChange = { contents = it },
                    testTag = "descriptionTextField",
                    )

                CardCreationPanelFormSection(
                    title = "태그",
                    placeholder = "태그를 쉼표로 구분하여 입력하세요 (예: 버그, 긴급)",
                    value = tempTags,
                    onTextChange = {
                        tempTags = it
                        tagInfoText = Card.isValidTagInfo(tempTags)
                    },
                    showAdditionalInfo = true,
                    testTag = "tagTextField",
                    infoText = tagInfoText,
                    isError = !Card.isValidTag(tempTags),
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
                    onCancelClick = { onShowCardCreationPanel(false) },
                    onCreateClick = {
                        onAddItem(
                            Card.create(
                                title = taskTitle,
                                content = contents,
                                tags = tags,
                                manager = manager,
                                state = state,
                            ),
                        )
                        onShowCardCreationPanel(false)
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
    onCancelClick: () -> Unit,
    onCreateClick: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ActionButton(
            buttonType = ActionButtonType.SECONDARY,
            enabled = true,
            onClick = onCancelClick,
        )
        Spacer(modifier = Modifier.width(12.dp))
        ActionButton(
            buttonType = ActionButtonType.PRIMARY,
            enabled = createEnabled,
            onClick = onCreateClick,
        )
    }
}