package woowacourse.kanban.study

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import kotlin.test.Test

enum class TaskStatus {
    TODO,
    IN_PROGRESS,
    DONE
}

class Task(
    //val을 붙여서 바로 프로퍼티로 만들 수 있음.
    val title: String,
    val description: String? = null,
    val tags: List<String>? = null,
    val status: TaskStatus,
    val assignee: String,
) {
    fun doesDescriptionIsEmpty() = description == null
    fun doesTagsIsEmpty() = tags == null

    init {
        require(title.isNotBlank()) { "제목을 입력해주세요." }
        require(tags == null ||tags.size <= 5) { "태그는 5개까지만 생성 가능합니다." }
    }
}

class TaskTest {

    @Test
    fun `태스크는 제목, 설명, 태그, 상태, 담당자를 가진다`() {
        val task = Task(
            title = "TDD",
            description = "오늘 배운 내용을 복습하기",
            tags = listOf("공부", "TDD"),
            status = TaskStatus.TODO,
            assignee = "구름",
        )

        assertThat(task.title).isEqualTo("TDD")
        assertThat(task.description).isEqualTo("오늘 배운 내용을 복습하기")
        assertThat(task.tags).isEqualTo(listOf("공부", "TDD"))
        assertThat(task.status).isEqualTo(TaskStatus.TODO)
        assertThat(task.assignee).isEqualTo("구름")
    }

    @Test
    fun `제목이 빈 문자열이면 태스크 생성이 불가능하다`() {
        assertThatThrownBy {
            val task = Task(
                title = "",
                description = "오늘 배운 내용을 복습하기",
                tags = listOf("공부", "TDD"),
                status = TaskStatus.TODO,
                assignee = "구름",
            )
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `태그는 최대 5개까지 생성 가능하다`() {
        assertThatThrownBy {
            val task = Task(
                title = "제목",
                description = "오늘 배운 내용을 복습하기",
                tags = listOf("TDD1", "TDD2", "TDD3", "TDD4", "TDD5", "TDD6"),
                status = TaskStatus.TODO,
                assignee = "구름",
            )
        }.isInstanceOf(IllegalArgumentException::class.java)

        val task = Task(
            title = "TDD",
            description = "오늘 배운 내용을 복습하기",
            tags = listOf("TDD1", "TDD2", "TDD3", "TDD4", "TDD5"),
            status = TaskStatus.TODO,
            assignee = "구름",
        )

        assertThat(task.title).isEqualTo("TDD")
        assertThat(task.description).isEqualTo("오늘 배운 내용을 복습하기")
        assertThat(task.tags).isEqualTo(listOf("TDD1", "TDD2", "TDD3", "TDD4", "TDD5"))
        assertThat(task.status).isEqualTo(TaskStatus.TODO)
        assertThat(task.assignee).isEqualTo("구름")
    }

    @Test
    fun `태스크는 제목, 상태, 담당자만으로 생성할 수 있다`() {
        val task = Task(
            title = "TDD",
            status = TaskStatus.TODO,
            assignee = "구름",
        )

        assertThat(task.title).isEqualTo("TDD")
        assertThat(task.status).isEqualTo(TaskStatus.TODO)
        assertThat(task.doesDescriptionIsEmpty()).isEqualTo(true)
        assertThat(task.doesTagsIsEmpty()).isEqualTo(true)
        assertThat(task.assignee).isEqualTo("구름")
    }

    @Test
    fun `상태를 지정하지 않으면 기본 값은 TODO다`() {
        val task = Task(
            title = "TDD",
            tags = emptyList(),
            status = TaskStatus.TODO,
            assignee = "구름",
        )
        assertThat(task.status).isEqualTo(TaskStatus.TODO)
    }
}