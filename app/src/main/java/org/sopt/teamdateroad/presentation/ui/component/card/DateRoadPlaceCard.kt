package org.sopt.teamdateroad.presentation.ui.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.teamdateroad.domain.model.Place
import org.sopt.teamdateroad.presentation.type.PlaceCardType
import org.sopt.teamdateroad.presentation.type.TagType
import org.sopt.teamdateroad.presentation.ui.component.tag.DateRoadTextTag
import org.sopt.teamdateroad.presentation.util.modifier.noRippleClickable
import org.sopt.teamdateroad.ui.theme.DateRoadTheme

@Composable
fun DateRoadPlaceCard(
    modifier: Modifier = Modifier,
    placeCardType: PlaceCardType,
    sequence: Int? = null,
    place: Place,
    onIconClick: (() -> Unit)? = null
) {
    val paddingValues = Modifier.padding(start = placeCardType.startPadding, end = 13.dp)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(76.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(14.dp))
                .background(DateRoadTheme.colors.gray100)
                .then(paddingValues)
        ) {
            Spacer(modifier = Modifier.padding(top = 13.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (placeCardType == PlaceCardType.COURSE_NORMAL) {
                    sequence?.let {
                        DateRoadTextTag(
                            textContent = (sequence + 1).toString(),
                            tagContentType = TagType.PLACE_CARD_NUMBER
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                }
                Text(
                    text = place.title,
                    modifier = Modifier.weight(1f),
                    style = DateRoadTheme.typography.bodyBold15,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.width(10.dp))

                DateRoadTextTag(
                    modifier = Modifier.width(61.dp),
                    textContent = place.duration,
                    tagContentType = TagType.PLACE_CARD_TIME
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                modifier = Modifier.padding(start = if (placeCardType == PlaceCardType.COURSE_NORMAL) 38.dp else 0.dp),
                text = place.address,
                color = DateRoadTheme.colors.gray300,
                style = DateRoadTheme.typography.bodyMed13,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(13.dp))
        }

        placeCardType.iconRes?.let {
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .width(44.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(14.dp))
                    .background(DateRoadTheme.colors.gray100)
            ) {
                Icon(
                    painter = painterResource(id = it),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(
                            start = placeCardType.iconStartPadding,
                            top = placeCardType.iconTopPadding,
                            end = placeCardType.iconEndPadding,
                            bottom = placeCardType.iconBottomPadding
                        )
                        .align(Alignment.Center)
                        .noRippleClickable {
                            onIconClick?.invoke()
                        }
                )
            }
        }
    }
}

@Preview
@Composable
fun DateRoadPlaceCardPreview() {
    Column {
        DateRoadPlaceCard(
            placeCardType = PlaceCardType.COURSE_NORMAL,
            sequence = 0,
            place = Place(title = "성수미술관 성수점성수미술관 성수점성수미술관 성수점성수미술관 성수점성수미술관 성수점", address = "서울 광진구 자양동 704-1", duration = "4.0시간")
        )
        Spacer(modifier = Modifier.height(8.dp))
        DateRoadPlaceCard(
            placeCardType = PlaceCardType.COURSE_EDIT,
            place = Place(title = "성수미술관 성수점", address = "서울 광진구 자양동 704-1", duration = "1시간"),
            onIconClick = { }
        )
        Spacer(modifier = Modifier.height(8.dp))
        DateRoadPlaceCard(
            placeCardType = PlaceCardType.COURSE_DELETE,
            place = Place(title = "성수미술관 성수점", address = "서울 광진구 자양동 704-1", duration = "0.5시간"),
            onIconClick = { }
        )
    }
}
