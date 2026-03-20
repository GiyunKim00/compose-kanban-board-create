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

    fun completionRate() : Int {
        return if(totalTaskCount() == 0) 0
        else doneTaskCount()* 100 / totalTaskCount()
    }

    fun addCard(card: Card): Board = Board(cardList + card)

    /**
    fun deleteCard(card: Card): Board = Board(cardList - card)

    fun updateCard(targetCard: Card, updatedCard: Card): Board = Board(
        cardList.map {
            if (it.id == targetCard.id) updatedCard
            else it
        }
    )
    */
}