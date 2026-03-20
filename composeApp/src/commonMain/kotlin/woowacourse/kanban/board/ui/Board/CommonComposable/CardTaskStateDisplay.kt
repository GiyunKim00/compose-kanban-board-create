package woowacourse.kanban.board.ui.Board.CommonComposable

import androidx.compose.runtime.Composable
import woowacourse.kanban.board.domain.Card.CardTaskState

@Composable
fun CardTaskState.toDisplayText(): String {
    return when (this) {
        CardTaskState.TODO -> "To Do"
        CardTaskState.IN_PROGRESS -> "In Progress"
        CardTaskState.DONE -> "Done"
    }
}