package woowacourse.kanban.domain.card

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class CardTaskState {
    TODO,
    IN_PROGRESS,
    DONE
}

enum class CardManagerState(
    val managerName: String,
) {
    DINO("다이노"),
    FAMES("페임스"),
}

enum class ActionButtonType(
    val buttonText: String,
    val contentColor: Color,
    val containerColor: Color,
    val elevation: Dp,
) {
    PRIMARY(
        buttonText = "생성",
        contentColor = Color.White,
        containerColor = Color(0xFF4F39F6),
        elevation = 3.dp,
    ),
    SECONDARY(
        buttonText = "취소",
        contentColor = Color(0xFF364153),
        containerColor = Color.White,
        elevation = 0.dp,
    ),
}