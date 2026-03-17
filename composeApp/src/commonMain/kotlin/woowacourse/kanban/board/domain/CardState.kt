package woowacourse.kanban.board.domain

enum class CardTaskState(
    val taskState: String,
) {
    TODO("To Do"),
    IN_PROGRESS("In Progress"),
    DONE("Done"),
}

enum class CardManagerState(
    val managerName: String,
) {
    DINO("다이노"),
    FAMES("페임스"),
}