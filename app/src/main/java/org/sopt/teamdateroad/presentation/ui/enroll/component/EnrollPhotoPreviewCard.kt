package org.sopt.teamdateroad.presentation.ui.enroll.component

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import org.sopt.teamdateroad.R
import org.sopt.teamdateroad.presentation.util.modifier.noRippleClickable
import org.sopt.teamdateroad.ui.theme.DATEROADTheme
import org.sopt.teamdateroad.ui.theme.DateRoadTheme

@Composable
fun EnrollPhotoPreviewCard(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    id: Int,
    isEditable: Boolean,
    isThumbnail: Boolean,
    image: String,
    onDeleteButtonClick: (Int) -> Unit = {},
    onSelectThumbnail: (Int) -> Unit = {}
) {
    val borderColor = if (isThumbnail && isEditable) DateRoadTheme.colors.purple600 else Color.Transparent
    Box(
        modifier = modifier
            .width(130.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(14.dp))
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = context)
                .data(image)
                .crossfade(true)
                .build(),
            placeholder = null,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(14.dp))
                .border(
                    width = 2.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(14.dp)
                )
                .noRippleClickable {
                    if (isEditable) {
                        onSelectThumbnail(id)
                    }
                }

        )
        if (isThumbnail) {
            Box(
                modifier = modifier
                    .width(56.dp)
                    .height(26.dp)
                    .background(DateRoadTheme.colors.purple600)
            ) {
                Text(
                    text = stringResource(R.string.enroll_thumbnail_text),
                    color = DateRoadTheme.colors.white,
                    style = DateRoadTheme.typography.bodySemi13,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        if (isEditable) {
            Image(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 6.dp, end = 6.dp)
                    .clip(CircleShape)
                    .background(DateRoadTheme.colors.gray200)
                    .padding(5.dp)
                    .noRippleClickable {
                        onDeleteButtonClick(id)
                    },
                painter = painterResource(id = R.drawable.ic_all_close),
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
fun EnrollPhotoPreviewCardPreview() {
    DATEROADTheme {
        EnrollPhotoPreviewCard(
            id = 0,
            isEditable = true,
            image = "",
            isThumbnail = true
        )
    }
}
