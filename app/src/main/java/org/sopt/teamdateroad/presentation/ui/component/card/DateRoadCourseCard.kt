package org.sopt.teamdateroad.presentation.ui.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import org.sopt.teamdateroad.R
import org.sopt.teamdateroad.domain.model.Course
import org.sopt.teamdateroad.presentation.type.TagType
import org.sopt.teamdateroad.presentation.ui.component.tag.DateRoadImageTag
import org.sopt.teamdateroad.presentation.util.modifier.noRippleClickable
import org.sopt.teamdateroad.ui.theme.DateRoadTheme

@Composable
fun DateRoadCourseCard(
    modifier: Modifier = Modifier,
    course: Course,
    onClick: (Int) -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(end = 16.dp)
            .height(130.dp)
            .background(DateRoadTheme.colors.white)
            .noRippleClickable(onClick = { onClick(course.courseId) })
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(top = 10.dp, bottom = 10.dp, start = 16.dp)
                .aspectRatio(1f)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(course.thumbnail)
                    .crossfade(true)
                    .build(),
                placeholder = null,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .matchParentSize()
                    .clip(RoundedCornerShape(14.dp))
            )
            DateRoadImageTag(
                textContent = course.like,
                imageContent = R.drawable.ic_tag_heart,
                tagContentType = TagType.HEART,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 5.dp, bottom = 5.dp, end = 5.dp)
            )
        }
        Column(
            modifier = Modifier
                .padding(start = 14.dp)
                .fillMaxHeight()
        ) {
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = course.city,
                style = DateRoadTheme.typography.bodyMed13,
                color = DateRoadTheme.colors.gray400,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = course.title,
                style = DateRoadTheme.typography.bodyBold15,
                color = DateRoadTheme.colors.black,
                modifier = Modifier
                    .fillMaxWidth(),
                minLines = 2,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                DateRoadImageTag(
                    textContent = course.cost,
                    imageContent = R.drawable.ic_all_money_12,
                    tagContentType = TagType.MONEY
                )
                Spacer(modifier = Modifier.size(6.dp))
                DateRoadImageTag(
                    textContent = course.duration,
                    imageContent = R.drawable.ic_all_clock_12,
                    tagContentType = TagType.TIME
                )
            }
        }
    }
}

@Preview
@Composable
fun DateRoadCourseCardPreview() {
    Column {
        DateRoadCourseCard(
            course = Course(
                courseId = 1,
                thumbnail = "https://avatars.githubusercontent.com/u/103172971?v=4",
                city = "건대/성수/왕십리",
                title = "여기 야키니쿠 꼭 먹으러 가세요\n하지만 일본에 있습니다.",
                cost = "10만원 초과",
                duration = "10시간",
                like = "999"
            )
        )
    }
}
