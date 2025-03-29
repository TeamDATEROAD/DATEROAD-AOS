package org.sopt.teamdateroad.presentation.ui.enroll.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.teamdateroad.domain.model.PlaceInfo
import org.sopt.teamdateroad.presentation.util.modifier.noRippleClickable
import org.sopt.teamdateroad.ui.theme.DATEROADTheme
import org.sopt.teamdateroad.ui.theme.DateRoadTheme

@Composable
fun EnrollPlaceSearchItem(
    keyword: String,
    placeInfo: PlaceInfo,
    onClick: (PlaceInfo) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .noRippleClickable {
                onClick(placeInfo)
            }
            .padding(horizontal = 25.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(11.dp))
        Text(
            text = getHighlightedPlaceName(keyword, placeInfo.placeName),
            color = DateRoadTheme.colors.black,
            style = DateRoadTheme.typography.bodySemi15,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = placeInfo.addressName,
            color = DateRoadTheme.colors.gray300,
            style = DateRoadTheme.typography.bodySemi13,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun getHighlightedPlaceName(keyword: String, placeName: String) = buildAnnotatedString {
    val keywordIndex = findKeywordIndex(keyword, placeName)

    if (keywordIndex != null) {
        append(placeName.substring(0, keywordIndex.first))
        withStyle(style = SpanStyle(color = DateRoadTheme.colors.purple600)) {
            append(placeName.substring(keywordIndex.first, keywordIndex.second + 1))
        }
        append(placeName.substring(keywordIndex.second + 1, placeName.length))
    } else {
        append(placeName)
    }
}

private fun findKeywordIndex(keyword: String, placeName: String): Pair<Int, Int>? {
    val normalizedKeyword = keyword.replace(" ", "")
    val normalizedPlaceName = placeName.replace(" ", "")

    val startIndex = normalizedPlaceName.indexOf(normalizedKeyword)
    if (startIndex == -1) return null

    var actualStartIndex = 0
    var nonSpaceCount = 0
    for ((i, ch) in placeName.withIndex()) {
        if (ch != ' ') nonSpaceCount++
        if (nonSpaceCount == startIndex + 1) {
            actualStartIndex = i
            break
        }
    }

    var actualEndIndex = actualStartIndex
    var matchedCount = 0
    for (i in actualStartIndex until placeName.length) {
        if (placeName[i] != ' ') matchedCount++
        if (matchedCount == normalizedKeyword.length) {
            actualEndIndex = i
            break
        }
    }

    return actualStartIndex to actualEndIndex
}

@Preview(showBackground = true)
@Composable
fun EnrollPlacerSearchItemPreview() {
    DATEROADTheme {
        EnrollPlaceSearchItem(
            keyword = "카페",
            placeInfo = PlaceInfo("의왕 카페 나랑", "경기 의왕시 청계로 217"),
            onClick = {}
        )
    }
}
