package woowacourse.kanban.board

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import woowacourse.kanban.board.domain.CardData
import woowacourse.kanban.board.ui.CardCreationPanel

@Composable
@Preview
fun App() {
    MaterialTheme {
        val kanbanBoardCards = remember { mutableStateListOf<CardData>() }
        var showInputWindow by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier.fillMaxSize(),
        ) {

            if (showInputWindow) {
                CardCreationPanel(
                    onAddItem = { kanbanBoardCards.add(it) },
                    onShowInputWindow = { showInputWindow = it },
                )
            }
        }
    }
}
