package woowacourse.kanban.board.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import woowacourse.kanban.board.domain.CardData

@Composable
fun CardCreationPanel(
    onAddItem: (CardData) -> Unit,
    onShowInputWindow: (Boolean) -> Unit,
) {
    var title by remember { mutableStateOf("LazyColumn 컴포넌트 구현") }
    var contents by remember { mutableStateOf("") }
    var tempTag by remember { mutableStateOf("") }
    var tags by remember { mutableStateOf(listOf<String>()) }
    var author by remember { mutableStateOf("다이노") }

    OutlinedCard(modifier = Modifier.padding(10.dp)) {
        Column(modifier = Modifier.padding(10.dp)) {
            // 제목 입력
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("제목 입력") },
                modifier = Modifier.fillMaxWidth(),
            )

            // 내용 입력
            OutlinedTextField(
                value = contents,
                onValueChange = { contents = it },
                label = { Text("내용 입력") },
                modifier = Modifier.fillMaxWidth(),
            )

            // 태그 입력
            OutlinedTextField(
                value = tempTag,
                onValueChange = { tempTag = it },
                label = { Text("태그 입력 후 엔터") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (tempTag.isNotBlank() && !tags.contains(tempTag)) {
                            tags = tags + tempTag.trim()
                            tempTag = ""
                        }
                    },
                ),
            )

            /**
             * 작성자 입력
             * @param author 작성자
             * @param onAuthorChange 작성자 변경 콜백
             * @param onAddItem 추가 콜백
             */
            OutlinedTextField(
                value = author,
                onValueChange = { author = it },
                label = { Text("작성자 입력") },
                modifier = Modifier.fillMaxWidth(),
            )

            // 확인 버튼
            Button(
                onClick = {
                    onAddItem(CardData.create(title, contents, tags, author))
                    onShowInputWindow(false)
                },
                modifier = Modifier.align(Alignment.End),
            ) {
                Text("확인")
            }
        }
    }
}