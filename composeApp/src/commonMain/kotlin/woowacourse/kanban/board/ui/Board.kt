package woowacourse.kanban.board.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.kanban.board.domain.CardData

@Composable
fun Board() {
    val cards = remember { mutableStateListOf<CardData>() }
    var showCardCreationPanel by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp, vertical = 16.dp),
        ) {
            BoardHeaderSection(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    showCardCreationPanel = true
                },
            )
            BoardContents(
                modifier = Modifier.fillMaxSize(),
                cards = cards
            )
        }

        if (showCardCreationPanel) {
            CardCreationPanel(
                modifier = Modifier.align(Alignment.Center),
                onAddItem = { cards.add(it) },
                onShowCardCreationPanel = { showCardCreationPanel = it },
            )
        }
    }
}

@Composable
private fun BoardHeaderSection(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = "Compose Desktop 칸반 보드 ",
                    fontWeight = FontWeight.W500,
                    fontSize = 24.sp,
                    lineHeight = 32.sp,
                    letterSpacing = 0.07.sp,
                )
                Text(
                    text = "완료율: 0% (0/0)",
                    fontWeight = FontWeight.W400,
                    color = Color(0xFF6A7282),
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    letterSpacing = (-0.15).sp,
                )
            }

            Button(
                onClick = { onClick() },
                modifier = Modifier,
                shape = RoundedCornerShape(20),
                ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "새 태스크 생성 아이콘",
                        tint = Color.White,
                    )
                    Text(
                        text = "새 태스크 생성",
                        color = Color.White,
                        fontWeight = FontWeight.W400,
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        letterSpacing = (-0.31).sp,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LinearProgressIndicator(
            progress = { 0.5f },
            modifier = Modifier.fillMaxWidth().height(8.dp),
            color = ProgressIndicatorDefaults.linearColor,
            trackColor = ProgressIndicatorDefaults.linearTrackColor,
            strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
        )
    }
}

/**
 * 테스트 용 보드 컨텐츠 간단 출력 모듈입니다. (추후 단계에서 구현 예정)
 */
@Composable
private fun BoardContents(
    modifier: Modifier = Modifier,
    cards: List<CardData>
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(cards.size) {item ->
            Card(cardData = cards[item])
        }
    }
}