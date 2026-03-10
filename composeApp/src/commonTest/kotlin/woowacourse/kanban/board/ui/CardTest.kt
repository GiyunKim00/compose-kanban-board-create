package woowacourse.kanban.board.ui

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import woowacourse.kanban.board.domain.CardData
import kotlin.test.Test

/**
 * [Card] UI 테스트 클래스입니다.
 */
@OptIn(ExperimentalTestApi::class)
class CardTest {

    @Test
    fun `KanbanBoardCard의 제목, 내용, 태그, 계정명이 입력되면, 해당 필드가 모두 표시된다`() = runComposeUiTest {
        val cardData = CardData.create(
            title = "제목",
            content = "내용",
            tags = listOf("태그1", "태그2", "태그3", "태그4", "태그5"),
            accountName = "테스트 계정",
        )

        setContent {
            Card(cardData = cardData)
        }

        onNodeWithText("제목").assertIsDisplayed()
        onNodeWithText("내용").assertIsDisplayed()
        onNodeWithText("태그1").assertIsDisplayed()
        onNodeWithText("태그2").assertIsDisplayed()
        onNodeWithText("태그3").assertIsDisplayed()
        onNodeWithText("태그4").assertIsDisplayed()
        onNodeWithText("태그5").assertIsDisplayed()
        onNodeWithText("테스트 계정").assertIsDisplayed()
    }

    @Test
    fun `제목과 계정명이 화면에 표시된다`() = runComposeUiTest {
        val cardData = CardData.create(
            title = "제목",
            content = "",
            tags = emptyList(),
            accountName = "테스트 계정",
        )

        setContent {
            Card(cardData = cardData)
        }
        onNodeWithContentDescription("Card Title").assertIsDisplayed()
        onNodeWithContentDescription("Card Content").assertDoesNotExist()
        onAllNodesWithContentDescription("Card Tag").assertCountEquals(0)
        onNodeWithContentDescription("Card Account Info").assertIsDisplayed()
    }

    @Test
    fun `내용이 없는 경우, 내용 영역이 표시되지 않는다`() = runComposeUiTest {
        val tags = listOf("태그1", "태그2", "태그3", "태그4")
        val cardData = CardData.create(
            title = "제목",
            content = "",
            tags = tags,
            accountName = "테스트 계정",
        )

        setContent {
            Card(cardData = cardData)
        }
        onNodeWithContentDescription("Card Title").assertIsDisplayed()
        onNodeWithContentDescription("Card Content").assertDoesNotExist()
        onAllNodesWithContentDescription("Card Tag").assertCountEquals(tags.size)
        onNodeWithContentDescription("Card Account Info").assertIsDisplayed()
    }

    @Test
    fun `태그가 없는 경우, 태그가 출력되지 않는다`() = runComposeUiTest {
        val cardData = CardData.create(
            title = "제목",
            content = "내용",
            tags = emptyList(),
            accountName = "테스트 계정",
        )

        setContent {
            Card(cardData = cardData)
        }

        onNodeWithContentDescription("Card Title").assertIsDisplayed()
        onNodeWithContentDescription("Card Content").assertIsDisplayed()
        onAllNodesWithContentDescription("Card Tag").assertCountEquals(0)
        onNodeWithContentDescription("Card Account Info").assertIsDisplayed()
    }
}