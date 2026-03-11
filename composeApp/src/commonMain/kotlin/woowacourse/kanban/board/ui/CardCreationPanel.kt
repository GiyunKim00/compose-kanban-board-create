package woowacourse.kanban.board.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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