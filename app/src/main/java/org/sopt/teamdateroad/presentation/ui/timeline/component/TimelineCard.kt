package org.sopt.teamdateroad.presentation.ui.timeline.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.sopt.teamdateroad.R
import org.sopt.teamdateroad.domain.model.Timeline
import org.sopt.teamdateroad.presentation.type.DateTagType
import org.sopt.teamdateroad.presentation.type.TagType
import org.sopt.teamdateroad.presentation.type.TimelineType
import org.sopt.teamdateroad.presentation.ui.component.tag.DateRoadImageTag
import org.sopt.teamdateroad.presentation.ui.component.tag.DateRoadTextTag
import org.sopt.teamdateroad.presentation.util.modifier.noRippleClickable
import org.sopt.teamdateroad.ui.theme.DateRoadTheme
import org.sopt.teamdateroad.ui.theme.defaultDateRoadColors

@Composable
fun TimelineCard(
    timeline: Timeline,
    timelineType: TimelineType,
    onClick: (Int) -> Unit = {},
    paddingEnd: Dp = 0.dp
) {
    Box(
        modifier = Modifier
            .aspectRatio(291 / 406f)
            .padding(end = paddingEnd)
            .clip(RoundedCornerShape(24.dp))
            .background(timelineType.backgroundColor)
            .noRippleClickable(onClick = { onClick(timeline.timelineId) })
    ) {
        Icon(
            painter = painterResource(id = R.drawable.bg_timeline_card),
            contentDescription = null,
            tint = timelineType.lineColor,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(
                            text = timeline.date,
                            style = DateRoadTheme.typography.titleExtra24,
                            color = DateRoadTheme.colors.black,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    DateRoadTextTag(
                        textContent = timeline.dDay,
                        tagContentType = TagType.TIMELINE_D_DAY
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(start = 20.dp, end = 20.dp, bottom = 21.dp),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.Start
                ) {
                    if (timeline.tags.size >= 3) {
                        DateRoadImageTag(
                            textContent = stringResource(id = timeline.tags[2].titleRes),
                            imageContent = timeline.tags[2].imageRes,
                            tagContentType = timelineType.tagType,
                            spaceValue = 2,
                            modifier = Modifier
                                .graphicsLayer(rotationZ = -12f)
                                .padding(start = 19.dp, bottom = 5.dp)
                        )
                    }
                    if (timeline.tags.size >= 2) {
                        DateRoadImageTag(
                            textContent = stringResource(id = timeline.tags[1].titleRes),
                            imageContent = timeline.tags[1].imageRes,
                            tagContentType = timelineType.tagType,
                            spaceValue = 2,
                            modifier = Modifier
                                .graphicsLayer(rotationZ = 15f)
                                .padding(start = 60.dp, bottom = 10.dp)
                        )
                    }
                    if (timeline.tags.isNotEmpty()) {
                        DateRoadImageTag(
                            textContent = stringResource(id = timeline.tags[0].titleRes),
                            imageContent = timeline.tags[0].imageRes,
                            tagContentType = timelineType.tagType,
                            spaceValue = 2
                        )
                    }
                }
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
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 24.dp)
            ) {
                Text(
                    text = timeline.city,
                    style = DateRoadTheme.typography.bodyMed15,
                    color = DateRoadTheme.colors.gray500,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = timeline.title,
                    style = DateRoadTheme.typography.titleExtra24,
                    color = DateRoadTheme.colors.black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview
@Composable
fun TimelineCardPreview() {
    Column {
        TimelineCard(
            timelineType = TimelineType.PURPLE,
            timeline = Timeline(
                timelineId = 0,
                dDay = "3",
                title = "성수동 당일치기 데이트 가볼까요?",
                date = "JUNE.23",
                city = "건대/성수/왕십리",
                tags = listOf(DateTagType.SHOPPING, DateTagType.DRIVE, DateTagType.EXHIBITION_POPUP)
            )
        )
    }
}
