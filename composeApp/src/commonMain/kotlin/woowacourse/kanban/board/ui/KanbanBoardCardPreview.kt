package woowacourse.kanban.board.ui

import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import woowacourse.kanban.board.domain.KanbanBoardCardData

/**
 * 여러 케이스에 따른 KanbanCard의 Preview를 모아볼 수 있습니다.
 */
private class KanbanCardPreviewParameterProvider : PreviewParameterProvider<KanbanBoardCardData> {
    override val values: Sequence<KanbanBoardCardData> = sequenceOf(
        KanbanBoardCardData.create(
            title = "Lazy Column 컴포넌트 구현",
            content = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.",
            tags = listOf("컴포넌트", "성능"),
            accountName = "구름",
        ),
        KanbanBoardCardData.create(
            title = "Lazy Column 컴포넌트 구현",
            content = "",
            tags = listOf("컴포넌트", "성능"),
            accountName = "구름",
        ),
        KanbanBoardCardData.create(
            title = "Lazy Column 컴포넌트 구현",
            content = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.",
            tags = emptyList(),
            accountName = "구름",
        ),
        KanbanBoardCardData.create(
            title = "Lazy Column 컴포넌트 구현",
            content = "",
            tags = emptyList(),
            accountName = "구름",
        ),
        KanbanBoardCardData.create(
            title = "너무너무 긴 제목은 한 줄까지만 노출너무너무 긴 제목은 한 줄까지만 노출",
            content = "너무너무너무 긴 설명은 두 줄까지만 노출하고 말줄임표로 처리합니다 두 줄까지만 노너무너무너무 긴 설명은 두 줄까지만 노출하고 말줄임표로 처리합니다 두 줄까지만 노",
            tags = listOf("너무너무", "긴 태그", "최대로", "5자까지진짜로", "5개제한임", "6개"),
            accountName = "너무너무너무 긴 담당자도 한 줄너무너무너무 긴 담당자도 한 줄",
        ),
    )
}

@Preview(showBackground = true, name = "KanbanBoardCard")
@Composable
private fun KanbanBoardCardPreview(
    @PreviewParameter(KanbanCardPreviewParameterProvider::class) kanbanBoardCardData: KanbanBoardCardData,
) {
    KanbanBoardCard(
        modifier = Modifier.width(286.dp),
        kanbanBoardCardData = kanbanBoardCardData,
    )
}