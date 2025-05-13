package org.sopt.teamdateroad.presentation.ui.component.bottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.teamdateroad.presentation.ui.component.bottomsheet.model.Picker
import org.sopt.teamdateroad.presentation.ui.component.numberpicker.DateRoadNumberPicker
import org.sopt.teamdateroad.presentation.ui.component.numberpicker.state.PickerState
import org.sopt.teamdateroad.presentation.util.DatePicker
import org.sopt.teamdateroad.ui.theme.DateRoadTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRoadDatePickerBottomSheet(
    isBottomSheetOpen: Boolean,
    isButtonEnabled: Boolean,
    buttonText: String,
    onDatePickerBottomSheetButtonClick: (String) -> Unit,
    onDismissRequest: () -> Unit = {}
) {
    val yearItems = (DatePicker.YEAR_START..DatePicker.YEAR_END).map { it.toString() }
    val monthItems = (DatePicker.MONTH_START..DatePicker.MONTH_END).map { it.toString().padStart(2, '0') }

    val yearState = remember { PickerState() }
    val monthState = remember { PickerState() }
    val dayState = remember { PickerState() }
    val dayItemsState = remember { mutableStateOf((1..31).map { it.toString().padStart(2, '0') }) }

    LaunchedEffect(yearState.selectedItem, monthState.selectedItem) {
        val year = yearState.selectedItem.toIntOrNull() ?: DatePicker.YEAR_START
        val month = monthState.selectedItem.toIntOrNull() ?: DatePicker.MONTH_START
        val lastDay = getLastDayOfMonth(year, month)
        val newDays = (DatePicker.DAY_START..lastDay).map { it.toString().padStart(2, '0') }

        dayItemsState.value = newDays
        if (dayState.selectedItem !in newDays) {
            dayState.selectedItem = newDays.first()
        }
    }

    val yearPicker = Picker(
        items = yearItems,
        startIndex = yearItems.indexOf(yearState.selectedItem),
        pickerState = yearState
    )
    val monthPicker = Picker(
        items = monthItems,
        startIndex = monthItems.indexOf(monthState.selectedItem),
        pickerState = monthState
    )
    val dayPicker = Picker(
        items = dayItemsState.value,
        startIndex = dayItemsState.value.indexOf(dayState.selectedItem),
        pickerState = dayState
    )

    val pickers = listOf(yearPicker, monthPicker, dayPicker)

    LaunchedEffect(Unit) {
        yearState.selectedItem = yearItems[DatePicker.YEAR_START_INDEX]
        monthState.selectedItem = monthItems.first()
        dayState.selectedItem = dayItemsState.value.first()
    }

    DateRoadBottomSheet(
        modifier = Modifier.padding(top = 20.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
        isBottomSheetOpen = isBottomSheetOpen,
        isButtonEnabled = isButtonEnabled,
        buttonText = buttonText,
        onButtonClick = {
            val selectedDate = pickers.joinToString(separator = DatePicker.SEPARATOR) {
                it.pickerState.selectedItem.padStart(2, '0')
            }
            onDatePickerBottomSheetButtonClick(selectedDate)
            // 상태 초기화
            yearState.selectedItem = yearItems[DatePicker.YEAR_START_INDEX]
            monthState.selectedItem = monthItems.first()
            dayState.selectedItem = dayItemsState.value.first()
        },
        onDismissRequest = {
            yearState.selectedItem = yearItems[DatePicker.YEAR_START_INDEX]
            monthState.selectedItem = monthItems.first()
            dayState.selectedItem = dayItemsState.value.first()
            onDismissRequest()
        }
    ) {
        Column {
            Row(modifier = Modifier.fillMaxWidth()) {
                pickers.forEachIndexed { index, item ->
                    DateRoadNumberPicker(
                        modifier = Modifier.weight(1f),
                        items = item.items,
                        startIndex = if (index == 0)item.items.indexOf(item.pickerState.selectedItem) else 0,
                        pickerState = item.pickerState
                    )
                    if (index != pickers.size - 1) {
                        Spacer(modifier = Modifier.width(17.dp))
                    }
                }
            }
            Spacer(modifier = Modifier.height(19.dp))
        }
    }
}

fun getLastDayOfMonth(year: Int, month: Int): Int {
    return when (month) {
        1, 3, 5, 7, 8, 10, 12 -> 31
        4, 6, 9, 11 -> 30
        2 -> if (isLeapYear(year)) 29 else 28
        else -> 31
    }
}

fun isLeapYear(year: Int): Boolean {
    return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
}

@Preview
@Composable
fun DateRoadDatePickerBottomSheetPreview() {
    var isBottomSheetOpen by rememberSaveable { mutableStateOf(false) }

    Button(onClick = { isBottomSheetOpen = true }) {
        Text(
            text = "DateRoadPickerBottomSheet",
            color = DateRoadTheme.colors.black,
            style = DateRoadTheme.typography.titleExtra24
        )
    }

    DateRoadDatePickerBottomSheet(
        isBottomSheetOpen = isBottomSheetOpen,
        isButtonEnabled = true,
        buttonText = "취소",
        onDatePickerBottomSheetButtonClick = {}
    )
}
