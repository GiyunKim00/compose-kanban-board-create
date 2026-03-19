package woowacourse.kanban.board.domain.Board

import woowacourse.kanban.board.domain.Card.Card
import woowacourse.kanban.board.domain.Card.CardTaskState

class Board(
    private val cardList: List<Card> = emptyList(),
) {
    fun cards(): List<Card> = cardList

    fun totalTaskCount(): Int = cardList.size

    fun doneTaskCount(): Int = cardList.count { it.state == CardTaskState.DONE }

    fun inProgressTaskCount(): Int = cardList.count { it.state == CardTaskState.IN_PROGRESS}

    fun toDoTaskCount(): Int = cardList.count { it.state == CardTaskState.TODO}

    fun completionRate() : Int {
        return if(totalTaskCount() == 0) 0
        else doneTaskCount()* 100 / totalTaskCount()
    }

    fun addCard(card: Card): Board = Board(cardList + card)

    fun deleteCard(card: Card): Board = Board(cardList - card)

    fun updateCard(targetCard : Card, updatedCard : Card) : Board = Board(
        cardList.map {
            if(it == targetCard) updatedCard
            else it
        }
    )

    fun countByState(state: CardTaskState): Int = cardList.count { it.state == state }

}