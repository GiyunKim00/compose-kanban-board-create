package woowacourse.kanban.board.domain.Board

import woowacourse.kanban.board.domain.Card.Card
import woowacourse.kanban.board.domain.Card.CardTaskState

class Board(
    private val cardList: List<Card> = emptyList(),
) {
    fun cards(): List<Card> = cardList

    fun totalTaskCount(): Int = cardList.size

    fun doneTaskCount(): Int = cardList.count { it.taskState == CardTaskState.DONE }

    fun inProgressTaskCount(): Int = cardList.count { it.taskState == CardTaskState.IN_PROGRESS}

    fun toDoTaskCount(): Int = cardList.count { it.taskState == CardTaskState.TODO}
}