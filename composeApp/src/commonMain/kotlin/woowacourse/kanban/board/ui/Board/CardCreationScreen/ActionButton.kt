package woowacourse.kanban.board.ui.Board.CardCreationScreen

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import woowacourse.kanban.board.domain.Card.ActionButtonType

@Composable
fun ActionButton(
    buttonType: ActionButtonType,
    enabled: Boolean,
    onClick: () -> Unit = {},
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = buttonType.elevation,
            pressedElevation = buttonType.elevation
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonType.containerColor,
            contentColor = buttonType.contentColor,
        ),
        shape = RoundedCornerShape(20),
    ) {
        Text(
            text = buttonType.buttonText,
            color = buttonType.contentColor,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            letterSpacing = (-0.3).sp,
            lineHeight = 24.sp,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EnabledActionButtonPreview() {
    ActionButton(
        buttonType = ActionButtonType.PRIMARY,
        enabled = true,
    )
}

@Preview(showBackground = true)
@Composable
private fun DisabledActionButtonPreview() {
    ActionButton(
        buttonType = ActionButtonType.PRIMARY,
        enabled = false,
    )
}