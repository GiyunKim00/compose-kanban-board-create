package woowacourse.kanban.board.ui

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class RecompositionTest {
    var username by mutableStateOf("")
    var label by mutableStateOf("")
    var validationCount = 0

    @Before
    fun setup() {
        username = ""
        label = "라벨"
        validationCount = 0
    }

    @Composable
    fun Username(username: String, label: String) {
        val isError = (username.length in 2..5).also { validationCount++ }

        TextField(
            value = username,
            label = { Text(label) },
            isError = isError,
            onValueChange = { this.username = it },
        )
    }

    // value, label 변경 시 모두 validationCount 증가
    @Composable
    fun UsernameWithRemember(username: String, label: String) {
        val isError = remember { (username.length in 2..5).also { validationCount++ } }

        TextField(
            value = username,
            label = { if (isError) Text("에러") else Text(label) },
            isError = isError,
            onValueChange = { this.username = it },
        )
    }

    // remember 의 키로 넣은 값만 수정되었을 때 validationCount 증가
    @Composable
    fun UsernameWithRememberKey(username: String, label: String) {
        val isError = remember(username) { (username.length in 2..5).also { validationCount++ } }

        TextField(
            value = username,
            label = { if (isError) Text("에러") else Text(label) },
            isError = isError,
            onValueChange = { this.username = it },
        )
    }

    @Test
    fun `리컴포지션할 때 매번 유효성 검사`() = runComposeUiTest {
        setContent {
            Username(username = username, label = label)
        }

        username = "김컴포즈"
        waitForIdle()
        assertThat(validationCount).isEqualTo(1)

        label = "바뀐 라벨"
        waitForIdle()
        assertThat(validationCount).isEqualTo(2)
    }

    @Test
    fun `최초 컴포지션만 유효성 검사`() = runComposeUiTest {
        setContent {
            UsernameWithRemember(username, label)
        }

        waitForIdle()
        assertThat(validationCount).isEqualTo(1)
        username = "사무엘"
        waitForIdle()
        assertThat(validationCount).isEqualTo(1)
        onNodeWithText("에러").assertExists()
    }

    @Test
    fun `특정 값 변경시만 유효성 검사`() = runComposeUiTest {
        setContent {
            UsernameWithRememberKey(username, label)
        }

        username = "김컴포즈"
        waitForIdle()
        assertThat(validationCount).isEqualTo(1)

        label = "바뀐 라벨"
        waitForIdle()
        assertThat(validationCount).isEqualTo(1)
    }
}