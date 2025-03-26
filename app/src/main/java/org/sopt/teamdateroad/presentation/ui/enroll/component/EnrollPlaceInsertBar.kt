package org.sopt.teamdateroad.presentation.ui.enroll.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.teamdateroad.R
import org.sopt.teamdateroad.presentation.ui.component.bottomsheet.DateRoadPickerBottomSheet
import org.sopt.teamdateroad.presentation.ui.component.bottomsheet.model.Picker
import org.sopt.teamdateroad.presentation.ui.component.button.DateRoadImageButton
import org.sopt.teamdateroad.presentation.util.modifier.noRippleClickable
import org.sopt.teamdateroad.ui.theme.DATEROADTheme
import org.sopt.teamdateroad.ui.theme.DateRoadTheme

@Composable
fun EnrollPlaceInsertBar(
    modifier: Modifier = Modifier,
    placeName: String = "",
    duration: String = "",
    onPlaceSearchButtonClick: () -> Unit = {},
    onSelectedCourseTimeClick: () -> Unit = {},
    onAddCourseButtonClick: () -> Unit = {}
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            modifier = Modifier
                .weight(1f)
                .height(44.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = DateRoadTheme.colors.gray100
            ),
            shape = RoundedCornerShape(14.dp),
            contentPadding = PaddingValues(0.dp),
            onClick = onPlaceSearchButtonClick
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 14.dp)
                        .align(Alignment.CenterStart),
                    text = if (placeName.isEmpty()) stringResource(id = R.string.enroll_place_insert_bar_enter_place_placeholder) else placeName,
                    color = if (placeName.isEmpty()) DateRoadTheme.colors.gray300 else DateRoadTheme.colors.black,
                    style = DateRoadTheme.typography.bodySemi13,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Box(
            modifier = Modifier
                .width(77.dp)
                .height(44.dp)
                .background(color = DateRoadTheme.colors.gray100, shape = RoundedCornerShape(14.dp))
                .noRippleClickable(onClick = onSelectedCourseTimeClick),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = duration.ifEmpty { stringResource(id = R.string.enroll_place_insert_bar_select_course_time_placeholder) },
                color = if (duration.isEmpty()) DateRoadTheme.colors.gray300 else DateRoadTheme.colors.black,
                style = DateRoadTheme.typography.bodySemi13,
                maxLines = 1
            )
        }
        DateRoadImageButton(
            modifier = Modifier
                .size(44.dp),
            isEnabled = duration.isNotEmpty() && placeName.isNotEmpty(),
            cornerRadius = 14.dp,
            paddingHorizontal = 15.dp,
            paddingVertical = 15.dp,
            disabledBackgroundColor = DateRoadTheme.colors.gray100,
            disabledContentColor = DateRoadTheme.colors.gray300,
            onClick = { if (duration.isNotEmpty() && placeName.isNotEmpty()) onAddCourseButtonClick() else Unit }
        )
    }
}

@Preview
@Composable
fun EnrollPlaceInsertBarPreview() {
    DATEROADTheme {
        var duration by remember { mutableStateOf("") }
        var isBottomSheetOpen by rememberSaveable { mutableStateOf(false) }
        val pickerItems by remember {
            mutableStateOf(listOf(Picker(items = (1..12).map { (it * 0.5).toString() })))
        }

        EnrollPlaceInsertBar(
            placeName = "",
            duration = duration,
            onSelectedCourseTimeClick = {
                isBottomSheetOpen = true
            }
        )

        DateRoadPickerBottomSheet(
            isBottomSheetOpen = isBottomSheetOpen,
            isButtonEnabled = true,
            buttonText = "적용하기",
            pickers = pickerItems,
            onButtonClick = {
                duration = pickerItems[0].pickerState.selectedItem
                isBottomSheetOpen = false
            }
        )
    }
}
