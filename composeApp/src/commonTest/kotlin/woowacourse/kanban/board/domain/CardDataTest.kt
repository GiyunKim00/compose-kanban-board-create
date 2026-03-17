package woowacourse.kanban.board.domain

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * [CardData] Unit 테스트 클래스입니다.
 */
class CardDataTest {
    @Test
    fun `제목이 공백만 있으면 생성할 수 없다`() {
        assertFailsWith<IllegalArgumentException> {
            CardData.create(
                title = "   ",
                content = "내용",
                tags = listOf("태그1"),
                manager = CardManagerState.DINO,
                state = CardTaskState.TODO
            )
        }
    }

    @Test
    fun `계정명이 공백만 있으면 생성할 수 없다`() {
        assertFailsWith<IllegalArgumentException> {
            CardData.create(
                title = "제목",
                content = "내용",
                tags = listOf("태그1"),
                manager = CardManagerState.valueOf("   "),
                state = CardTaskState.TODO
            )
        }
    }

    @Test
    fun `내용이 있으면 hasContent 리턴 값은 true이다`() {
        val cardData = CardData.create(
            title = "제목",
            content = "내용",
            tags = emptyList(),
            manager = CardManagerState.DINO,
            state = CardTaskState.TODO
        )

        assertTrue(cardData.hasContent())
    }

    @Test
    fun `내용이 공백이면 hasContent 리턴 값은 false이다`() {
        val cardData = CardData.create(
            title = "제목",
            content = "   ",
            tags = emptyList(),
            manager = CardManagerState.DINO,
            state = CardTaskState.TODO
        )

        assertFalse(cardData.hasContent())
    }

    @Test
    fun `태그의 앞뒤 공백은 제거된다`() {
        val cardData = CardData.create(
            title = "제목",
            content = "내용",
            tags = listOf(" 태그1 ", "  태그2  "),
            manager = CardManagerState.DINO,
            state = CardTaskState.TODO
        )

        assertEquals(listOf("태그1", "태그2"), cardData.tags)
    }

    @Test
    fun `공백으로만 구성된 태그는 제거된다`() {
        val cardData = CardData.create(
            title = "제목",
            content = "내용",
            tags = listOf("태그1", "   ", "", "  "),
            manager = CardManagerState.DINO,
            state = CardTaskState.TODO
        )

        assertEquals(listOf("태그1"), cardData.tags)
    }

    @Test
    fun `태그가 5개를 초과하면 생성할 수 없다`() {
        assertFailsWith<IllegalArgumentException> {
            CardData.create(
                title = "제목",
                content = "내용",
                tags = listOf("태그1", "태그2", "태그3", "태그4", "태그5", "태그6"),
                manager = CardManagerState.DINO,
                state = CardTaskState.TODO
            )
        }
    }

    @Test
    fun `태그 내용이 5글자를 초과하면 5글자까지만 유지된다`() {
        assertFailsWith<IllegalArgumentException> {
            CardData.create(
                title = "제목",
                content = "내용",
                tags = listOf("우아한테크코스", "안드로이드8기", "칸반보드리팩터링"),
                manager = CardManagerState.DINO,
                state = CardTaskState.TODO
            )
        }
    }

    @Test
    fun `태그가 있으면 hasTag 리턴 값은 true이다`() {
        val cardData = CardData.create(
            title = "제목",
            content = "내용",
            tags = listOf("태그1", "   "),
            manager = CardManagerState.DINO,
            state = CardTaskState.TODO
        )

        assertTrue(cardData.hasTag())
    }

    @Test
    fun `태그가 비어 있으면 hasTag 리턴 값은 false이다`() {
        val cardData = CardData.create(
            title = "제목",
            content = "내용",
            tags = listOf("   ", ""),
            manager = CardManagerState.DINO,
            state = CardTaskState.TODO
        )

        assertFalse(cardData.hasTag())
    }

    @Test
    fun `잘못된 태그 문자열이 주어질 시 false가 반환된다`() {
        assertFalse(CardData.isValidTag(",..."))
    }

    @Test
    fun `잘못된 태그 문자열이 주어질 시 에러메시지가 반환된다`() {
        assertEquals("태그 형식이 올바르지 않습니다.", CardData.isValidTagInfo(",..."))
        assertEquals("태그는 5자 이내로 5개까지만 등록할 수 있습니다.", CardData.isValidTagInfo("태그1,태그2,태그3,태그4,태그5,태그6"))
    }

    @Test
    fun `쉼표를 기준으로 태그 문자열을 분리한다`() {
        assertEquals(
            listOf("태그1", "태그2", "태그3"),
            CardData.parseTag("태그1,태그2,태그3"),
        )
    }

    @Test
    fun `태그 문자열의 앞뒤 공백을 제거한 후 쉼표를 기준으로 분리한다`() {
        assertEquals(
            listOf("태그1", "태그2"),
            CardData.parseTag("태그1,태그2   "),
        )
    }
}