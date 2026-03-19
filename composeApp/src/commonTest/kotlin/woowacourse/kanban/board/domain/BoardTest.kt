package woowacourse.kanban.board.domain

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.assertj.core.api.Assertions.assertThat
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BoardTest {

    @Test
    fun `보드에 카드를 추가할 수 있다`(){
        val card = CardData.create(
            title = "제목",
            content = "내용내용",
            tags = listOf("태그"),
            manager = CardManagerState.DINO,
            state = CardTaskState.TODO
        )
        var board = Board().addCard(card)

        assertThat(board.totalTaskCount()).isEqualTo(1)
        board = board.addCard(card)
        assertThat(board.totalTaskCount()).isEqualTo(2)
    }

    @Test
    fun `보드에 카드를 삭제할 수 있다`() {
        val card1 = CardData.create(
            title = "제목1",
            content = "내용내용1",
            tags = listOf("태그1"),
            manager = CardManagerState.DINO,
            state = CardTaskState.TODO
        )
        val card2 = CardData.create(
            title = "제목2",
            content = "내용내용2",
            tags = listOf("태그2"),
            manager = CardManagerState.DINO,
            state = CardTaskState.TODO
        )
        var board = Board().addCard(card1).addCard(card2)

        assertThat(board.totalTaskCount()).isEqualTo(2)
        board = board.deleteCard(card1)
        assertThat(board.totalTaskCount()).isEqualTo(1)
    }

    @Test
    fun `보드에 카드를 수정할 수 있다`() {
        val card1 = CardData.create(
            title = "제목1",
            content = "내용내용1",
            tags = listOf("태그1"),
            manager = CardManagerState.DINO,
            state = CardTaskState.TODO
        )
        val card2 = CardData.create(
            title = "제목2",
            content = "내용내용2",
            tags = listOf("태그2"),
            manager = CardManagerState.DINO,
            state = CardTaskState.TODO
        )
        var board = Board().addCard(card1).addCard(card2)
        val updatedCard = CardData.create(
            title = "수정제목1",
            content = "수정내용내용",
            tags = listOf("수정태그"),
            manager = CardManagerState.DINO,
            state = CardTaskState.TODO
        )

        assertThat(board.totalTaskCount()).isEqualTo(2)
        board = board.updateCard(card1, updatedCard)
        assertThat(board.totalTaskCount()).isEqualTo(2)
        assertThat(board.cards()[0]).isEqualTo(updatedCard)
    }

    @Test
    fun `state에 따라 Card가 분류된다`() {
        val cardList = listOf(
            CardData.create(
                title = "제목1",
                content = "내용내용1",
                tags = listOf("태그1"),
                manager = CardManagerState.DINO,
                state = CardTaskState.TODO
            ),
            CardData.create(
                title = "제목2",
                content = "내용내용2",
                tags = listOf("태그2"),
                manager = CardManagerState.DINO,
                state = CardTaskState.TODO
            ),
            CardData.create(
                title = "제목3",
                content = "내용내용3",
                tags = listOf("태그3"),
                manager = CardManagerState.FAMES,
                state = CardTaskState.IN_PROGRESS
            ),
            CardData.create(
                title = "제목4",
                content = "내용내용4",
                tags = listOf("태그4"),
                manager = CardManagerState.FAMES,
                state = CardTaskState.DONE
            )
        )

        val board = Board(cardList)

        assertThat(board.toDoTaskCount()).isEqualTo(2)
        assertThat(board.inProgressTaskCount()).isEqualTo(1)
        assertThat(board.doneTaskCount()).isEqualTo(1)
    }

    @Test
    fun `카드가 없으면 빈 보드이다`() {
        val board = Board()

        assertThat(board.totalTaskCount()).isEqualTo(0)
        assertThat(board.doneTaskCount()).isEqualTo(0)
        assertThat(board.inProgressTaskCount()).isEqualTo(0)
        assertThat(board.toDoTaskCount()).isEqualTo(0)
    }

    @Test
    fun `카드가 없으면 완료율은 0%이다`() {
        val board = Board()

        assertThat(board.completionRate()).isEqualTo(0)
    }

    @Test
    fun `전체 카드 3개 중 1개만 완료되었다면 완료율은 33%이다`() {
        val cardList = listOf(
            CardData.create(
                title = "제목1",
                content = "내용내용1",
                tags = listOf("태그1"),
                manager = CardManagerState.DINO,
                state = CardTaskState.TODO
            ),
            CardData.create(
                title = "제목2",
                content = "내용내용2",
                tags = listOf("태그2"),
                manager = CardManagerState.DINO,
                state = CardTaskState.IN_PROGRESS
            ),
            CardData.create(
                title = "제목3",
                content = "내용내용3",
                tags = listOf("태그3"),
                manager = CardManagerState.FAMES,
                state = CardTaskState.DONE
            )
        )

        val board = Board(cardList)
        assertThat(board.completionRate()).isEqualTo(33)
    }

    @Test
    fun `카드 상태가 모두 완료되었다면 완료율은 100%이다`() {
        val cardList = listOf(
            CardData.create(
                title = "제목1",
                content = "내용내용1",
                tags = listOf("태그1"),
                manager = CardManagerState.DINO,
                state = CardTaskState.DONE
            ),
            CardData.create(
                title = "제목2",
                content = "내용내용2",
                tags = listOf("태그2"),
                manager = CardManagerState.DINO,
                state = CardTaskState.DONE
            ),
            CardData.create(
                title = "제목3",
                content = "내용내용3",
                tags = listOf("태그3"),
                manager = CardManagerState.FAMES,
                state = CardTaskState.DONE
            )
        )

        val board = Board(cardList)
        assertThat(board.completionRate()).isEqualTo(100)
    }

    @Test
    fun `중간에 카드를 삭제하거나 추가할 시 완료율이 변경된다`() {
        val cardList = listOf(
            CardData.create(
                title = "제목1",
                content = "내용내용1",
                tags = listOf("태그1"),
                manager = CardManagerState.DINO,
                state = CardTaskState.TODO
            ),
            CardData.create(
                title = "제목2",
                content = "내용내용2",
                tags = listOf("태그2"),
                manager = CardManagerState.DINO,
                state = CardTaskState.IN_PROGRESS
            ),
            CardData.create(
                title = "제목3",
                content = "내용내용3",
                tags = listOf("태그3"),
                manager = CardManagerState.FAMES,
                state = CardTaskState.DONE
            )
        )

        var board = Board(cardList)
        assertThat(board.completionRate()).isEqualTo(33)
        board = board.deleteCard(cardList[1])
        assertThat(board.completionRate()).isEqualTo(50)
        board = board.addCard(cardList[2])
        assertThat(board.completionRate()).isEqualTo(66)
    }
}