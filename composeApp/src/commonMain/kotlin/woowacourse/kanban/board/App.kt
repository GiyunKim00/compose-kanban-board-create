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
import androidx.compose.ui.tooling.preview.Devices.DESKTOP
import androidx.compose.ui.tooling.preview.Preview
import woowacourse.kanban.board.domain.CardData
import woowacourse.kanban.board.ui.Board
import woowacourse.kanban.board.ui.CardCreationPanel

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, device = DESKTOP)
fun App() {
    MaterialTheme {
        Board()
    }
}
