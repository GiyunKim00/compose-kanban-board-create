package woowacourse.kanban.board.domain

class Board(
    private val cardList: List<CardData> = emptyList(),
) {
    fun cards(): List<CardData> = cardList

    fun totalTaskCount(): Int = cardList.size

    fun doneTaskCount(): Int = cardList.count { it.state == CardTaskState.DONE }

    fun inProgressTaskCount(): Int = cardList.count { it.state == CardTaskState.IN_PROGRESS}

    fun toDoTaskCount(): Int = cardList.count { it.state == CardTaskState.TODO}

    fun completionRate() : Int {
        return if(totalTaskCount() == 0) 0
        else doneTaskCount()* 100 / totalTaskCount()
    }

    fun addCard(card: CardData): Board = Board(cardList + card)

    fun deleteCard(card: CardData): Board = Board(cardList - card)

    fun updateCard(targetCard : CardData, updatedCard : CardData) : Board = Board(
        cardList.map {
            if(it == targetCard) updatedCard
            else it
        }
    )

    fun countByState(state: CardTaskState): Int = cardList.count { it.state == state }

}