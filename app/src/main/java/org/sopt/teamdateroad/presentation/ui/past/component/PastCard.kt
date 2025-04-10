package org.sopt.teamdateroad.presentation.ui.past.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.teamdateroad.R
import org.sopt.teamdateroad.domain.model.Timeline
import org.sopt.teamdateroad.presentation.type.DateTagType
import org.sopt.teamdateroad.presentation.type.TimelineType
import org.sopt.teamdateroad.presentation.ui.component.tag.DateRoadImageTag
import org.sopt.teamdateroad.presentation.util.modifier.noRippleClickable
import org.sopt.teamdateroad.ui.theme.DATEROADTheme
import org.sopt.teamdateroad.ui.theme.DateRoadTheme
import org.sopt.teamdateroad.ui.theme.defaultDateRoadColors

@Composable
fun PastCard(
    timeline: Timeline,
    timelineType: TimelineType,
    onClick: (Int) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .aspectRatio(328 / 203f)
            .background(timelineType.backgroundColor)
            .noRippleClickable(onClick = { onClick(timeline.timelineId) })
    ) {
        Icon(
            painter = painterResource(id = R.drawable.bg_past_card),
            contentDescription = null,
            tint = timelineType.lineColor,
            modifier = Modifier
                .align(Alignment.BottomEnd)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .weight(1.5f)
                    .fillMaxWidth()
                    .padding(top = 14.dp, start = 16.dp, end = 16.dp)
            ) {
                Text(
                    text = timeline.date,
                    style = DateRoadTheme.typography.bodySemi13,
                    color = DateRoadTheme.colors.black,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = timeline.title,
                    style = DateRoadTheme.typography.titleExtra20,
                    color = DateRoadTheme.colors.black,
                    minLines = 2,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
            ) {
                val canvasWidth = size.width
                val dotLength = 3.dp.toPx()
                val dotSpacing = 3.dp.toPx()
                var xOffset = 0f
                val strokeWidth = 2.dp.toPx()

                drawCircle(
                    color = defaultDateRoadColors.white,
                    radius = strokeWidth * 4,
                    center = Offset(x = strokeWidth / 2 - 5, y = 0f)
                )

                while (xOffset < canvasWidth) {
                    drawLine(
                        color = defaultDateRoadColors.white,
                        start = Offset(xOffset, 0f),
                        end = Offset(xOffset + dotLength, 0f),
                        strokeWidth = 2.dp.toPx(),
                        cap = StrokeCap.Butt
                    )
                    xOffset += dotLength + dotSpacing
                }
                drawCircle(
                    color = defaultDateRoadColors.white,
                    radius = strokeWidth * 4,
                    center = Offset(x = canvasWidth - strokeWidth / 2 + 5, y = 0f)
                )
            }
            Spacer(modifier = Modifier.height(17.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = timeline.city,
                    style = DateRoadTheme.typography.bodyMed13,
                    color = DateRoadTheme.colors.black
                )
                LazyRow(
                    modifier = Modifier.padding(top = 10.dp)
                ) {
                    itemsIndexed(timeline.tags) { index, tag ->
                        DateRoadImageTag(
                            textContent = stringResource(id = tag.titleRes),
                            imageContent = tag.imageRes,
                            tagContentType = timelineType.tagType,
                            modifier = Modifier.padding(start = if (index > 0) 6.dp else 0.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PastCardPreview() {
    DATEROADTheme {
        PastCard(
            timeline = Timeline(
                dDay = "D-day",
                title = "성수동 당일치기 데이트 가볼까요?\n이정도 어떠신지?",
                date = "2024년 6월 24일",
                city = "건대/성수/왕십리",
                tags = listOf(DateTagType.FOOD, DateTagType.DRIVE)
            ),
            timelineType = TimelineType.PINK
        )
    }
}
