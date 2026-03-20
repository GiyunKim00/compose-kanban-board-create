package woowacourse.kanban.board.domain.Board

import woowacourse.kanban.board.domain.Card.Card
import woowacourse.kanban.board.domain.Card.CardManagerState
import woowacourse.kanban.board.domain.Card.CardTaskState

data class CardFormState(
    val title: String = "",
    val content: String = "",
    val tagInput: String = "",
    val taskState: CardTaskState = CardTaskState.TODO,
    val managerState: CardManagerState = CardManagerState.DINO,
) {
    val tags: List<String> = Card.parseTag(tagInput)
    val tagInfoText: String = Card.isValidTagInfo(tagInput)
    val isCreateEnabled: Boolean = Card.isValidText(title) && Card.isValidTag(tagInput)
}
