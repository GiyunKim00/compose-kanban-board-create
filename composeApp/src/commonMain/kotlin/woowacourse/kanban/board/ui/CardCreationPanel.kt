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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun CardCreationPanel(
    onAddItem: (CardData) -> Unit,
    onShowCardCreationPanel: (Boolean) -> Unit,
) {
    var taskTitle by remember { mutableStateOf("") }
    var contents by remember { mutableStateOf("") }
    var tags by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("To Do") }
    var manager by remember { mutableStateOf("다이노") }

    OutlinedCard {
        Column(
            modifier = Modifier.background(Color.White).width(672.dp),
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
                    onTextChange = { taskTitle = it },
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
                    value = tags,
                    onTextChange = { tags = it },
                    showAdditionalInfo = true,
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
    infoText: String = "5자 이내의 태그를 최대 5개까지 등록할 수 있습니다.",
) {
    Column(
        modifier = modifier,
    ) {
        TitleText(title)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = { onTextChange(it) },
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
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 1.sp,
            ),
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        )
        if (showAdditionalInfo) {
            Text(
                text = infoText,
                color = Color(0xFF49454F),
                fontSize = 12.sp,
                fontWeight = FontWeight.W400,
                lineHeight = 16.sp,
                modifier = Modifier.padding(top = 4.dp),
            )
        }
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