package org.sopt.teamdateroad.presentation.ui.component.bottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.holix.android.bottomsheetdialog.compose.BottomSheetBehaviorProperties
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialog
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties
import org.sopt.teamdateroad.R
import org.sopt.teamdateroad.domain.model.PlaceInfo
import org.sopt.teamdateroad.domain.model.PlaceSearchResult
import org.sopt.teamdateroad.presentation.ui.enroll.component.EnrollPlaceSearchItem
import org.sopt.teamdateroad.presentation.util.modifier.noRippleClickable
import org.sopt.teamdateroad.ui.theme.DATEROADTheme
import org.sopt.teamdateroad.ui.theme.DateRoadTheme

@Composable
fun DateRoadPlaceSearchBottomSheet(
    isBottomSheetOpen: Boolean,
    keyword: String,
    placeSearchResult: PlaceSearchResult,
    onKeywordChanged: (String) -> Unit,
    onPlaceSelected: (PlaceInfo) -> Unit,
    onDismissRequest: () -> Unit = {}
) {
    if (isBottomSheetOpen) {
        BottomSheetDialog(
            onDismissRequest = onDismissRequest,
            properties = BottomSheetDialogProperties(
                behaviorProperties = BottomSheetBehaviorProperties(
                    state = BottomSheetBehaviorProperties.State.HalfExpanded,
                    halfExpandedRatio = 0.8f,
                    skipCollapsed = true
                )
            )
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(DateRoadTheme.colors.white)
            ) {
                Spacer(modifier = Modifier.height(23.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .padding(start = 25.dp, end = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.enroll_place_search_bottom_sheet_title),
                        color = DateRoadTheme.colors.black,
                        style = DateRoadTheme.typography.bodyBold17
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Image(
                        modifier = Modifier
                            .size(40.dp)
                            .noRippleClickable(onClick = onDismissRequest)
                            .padding(15.dp),
                        painter = painterResource(id = R.drawable.ic_bottom_sheet_close),
                        contentDescription = null
                    )
                }

                Spacer(modifier = Modifier.height(22.dp))

                TextField(
                    value = keyword,
                    onValueChange = { keyword ->
                        onKeywordChanged(keyword)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .padding(start = 14.dp, end = 20.dp),
                    placeholder = {
                        Text(
                            modifier = Modifier,
                            text = stringResource(R.string.enroll_place_insert_bar_enter_place_placeholder),
                            color = DateRoadTheme.colors.gray300,
                            style = DateRoadTheme.typography.bodySemi15
                        )
                    },
                    trailingIcon = {
                        IconButton(
                            modifier = Modifier.size(20.dp),
                            onClick = {
                                onKeywordChanged("")
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.btn_enroll_delete_picture),
                                contentDescription = null,
                                tint = Color.Unspecified
                            )
                        }
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = DateRoadTheme.colors.gray100,
                        unfocusedContainerColor = DateRoadTheme.colors.gray100,
                        cursorColor = DateRoadTheme.colors.purple600,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(14.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                if (placeSearchResult.placeInfos.isNotEmpty()) {
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        itemsIndexed(items = placeSearchResult.placeInfos, key = { index, placeInfo -> placeInfo.hashCode() + index }) { index, placeInfo ->
                            EnrollPlaceSearchItem(keyword = keyword, placeInfo = placeInfo, onClick = { onPlaceSelected(placeInfo) })

                            if (index != placeSearchResult.placeInfos.lastIndex) {
                                HorizontalDivider(
                                    modifier = Modifier.fillMaxWidth(),
                                    color = DateRoadTheme.colors.gray100,
                                    thickness = 1.dp
                                )
                            }
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 70.dp)
                    ) {
                        Column(modifier = Modifier.align(Alignment.Center)) {
                            Image(
                                modifier = Modifier
                                    .width(167.dp)
                                    .height(191.dp),
                                painter = painterResource(R.drawable.img_place_search_no_match),
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.height(40.dp))
                            Text(
                                text = stringResource(R.string.enroll_place_search_no_match),
                                color = DateRoadTheme.colors.gray300,
                                style = DateRoadTheme.typography.titleBold18
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DateRoadPlaceSearchBottomSheetPreview() {
    DATEROADTheme {
        var isBottomSheetOpen by rememberSaveable { mutableStateOf(false) }
        var text by rememberSaveable { mutableStateOf("") }

        Button(onClick = { isBottomSheetOpen = true }) {
            Text(
                text = "DateRoadPlaceSearchBottomSheet",
                color = DateRoadTheme.colors.black,
                style = DateRoadTheme.typography.titleBold18
            )
        }

        DateRoadPlaceSearchBottomSheet(
            isBottomSheetOpen = isBottomSheetOpen,
            keyword = text,
            placeSearchResult = PlaceSearchResult(List(10) { PlaceInfo("카페 나랑", "경기 의왕시 청계로 217") }),
            onKeywordChanged = { text = it },
            onPlaceSelected = {},
            onDismissRequest = { isBottomSheetOpen = !isBottomSheetOpen }
        )
    }
}
