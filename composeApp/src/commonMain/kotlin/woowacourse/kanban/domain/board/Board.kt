package woowacourse.kanban.domain.board

import woowacourse.kanban.domain.card.Card
import woowacourse.kanban.domain.card.CardTaskState

class Board(
    private val cardList: List<Card> = emptyList(),
) {
    val cards: List<Card> = cardList
    val totalTaskCount: Int = cardList.size
    val doneTaskCount: Int = cardList.count { it.taskState == CardTaskState.DONE }
    val inProgressTaskCount: Int = cardList.count { it.taskState == CardTaskState.IN_PROGRESS }
    val toDoTaskCount: Int = cardList.count { it.taskState == CardTaskState.TODO }
    val completionRate: Int = if (totalTaskCount == 0) 0
    else doneTaskCount * 100 / totalTaskCount

    fun addCard(card: Card): Board = Board(cardList + card)
}