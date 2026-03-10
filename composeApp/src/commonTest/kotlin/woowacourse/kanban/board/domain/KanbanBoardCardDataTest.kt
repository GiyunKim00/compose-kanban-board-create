package woowacourse.kanban.board.domain

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * [KanbanBoardCardData] Unit 테스트 클래스입니다.
 */
class KanbanBoardCardDataTest {
    @Test
    fun `제목이 공백만 있으면 생성할 수 없다`() {
        assertFailsWith<IllegalArgumentException> {
            KanbanBoardCardData.create(
                title = "   ",
                content = "내용",
                tags = listOf("태그1"),
                accountName = "테스트 계정",
            )
        }
    }

    @Test
    fun `계정명이 공백만 있으면 생성할 수 없다`() {
        assertFailsWith<IllegalArgumentException> {
            KanbanBoardCardData.create(
                title = "제목",
                content = "내용",
                tags = listOf("태그1"),
                accountName = "    ",
            )
        }
    }

    @Test
    fun `내용이 있으면 hasContent 리턴 값은 true이다`() {
        val cardData = KanbanBoardCardData.create(
            title = "제목",
            content = "내용",
            tags = emptyList(),
            accountName = "테스트 계정",
        )

        assertTrue(cardData.hasContent())
    }

    @Test
    fun `내용이 공백이면 hasContent 리턴 값은 false이다`() {
        val cardData = KanbanBoardCardData.create(
            title = "제목",
            content = "   ",
            tags = emptyList(),
            accountName = "테스트 계정",
        )

        assertFalse(cardData.hasContent())
    }

    @Test
    fun `태그의 앞뒤 공백은 제거된다`() {
        val cardData = KanbanBoardCardData.create(
            title = "제목",
            content = "내용",
            tags = listOf(" 태그1 ", "  태그2  "),
            accountName = "테스트 계정",
        )

        assertEquals(listOf("태그1", "태그2"), cardData.tags)
    }

    @Test
    fun `공백으로만 구성된 태그는 제거된다`() {
        val cardData = KanbanBoardCardData.create(
            title = "제목",
            content = "내용",
            tags = listOf("태그1", "   ", "", "  "),
            accountName = "테스트 계정",
        )

        assertEquals(listOf("태그1"), cardData.tags)
    }

    @Test
    fun `태그가 5개를 초과하면 생성할 수 없다`() {
        assertFailsWith<IllegalArgumentException> {
            KanbanBoardCardData.create(
                title = "제목",
                content = "내용",
                tags = listOf("태그1", "태그2", "태그3", "태그4", "태그5", "태그6"),
                accountName = "테스트 계정",
            )
        }
    }

    @Test
    fun `태그 내용이 5글자를 초과하면 5글자까지만 유지된다`() {
        assertFailsWith<IllegalArgumentException> {
            KanbanBoardCardData.create(
                title = "제목",
                content = "내용",
                tags = listOf("우아한테크코스", "안드로이드8기", "칸반보드리팩터링"),
                accountName = "테스트 계정",
            )
        }
    }

    @Test
    fun `태그가 있으면 hasTag 리턴 값은 true이다`() {
        val cardData = KanbanBoardCardData.create(
            title = "제목",
            content = "내용",
            tags = listOf("태그1", "   "),
            accountName = "테스트 계정",
        )

        assertTrue(cardData.hasTag())
    }

    @Test
    fun `태그가 비어 있으면 hasTag 리턴 값은 false이다`() {
        val cardData = KanbanBoardCardData.create(
            title = "제목",
            content = "내용",
            tags = listOf("   ", ""),
            accountName = "테스트 계정",
        )

        assertFalse(cardData.hasTag())
    }
}