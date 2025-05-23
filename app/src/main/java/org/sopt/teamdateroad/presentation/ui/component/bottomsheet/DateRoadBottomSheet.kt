package org.sopt.teamdateroad.presentation.ui.component.bottomsheet

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.sopt.teamdateroad.presentation.ui.component.button.DateRoadBasicButton
import org.sopt.teamdateroad.ui.theme.DATEROADTheme
import org.sopt.teamdateroad.ui.theme.DateRoadTheme

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun DateRoadBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    isBottomSheetOpen: Boolean,
    isButtonEnabled: Boolean = true,
    buttonText: String,
    onButtonClick: () -> Unit = {},
    onDismissRequest: () -> Unit = {},
    content: @Composable () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    if (isBottomSheetOpen) {
        coroutineScope.launch {
            sheetState.show()
        }
        ModalBottomSheet(
            modifier = Modifier.padding(bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()),
            sheetState = sheetState,
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
            onDismissRequest = onDismissRequest,
            containerColor = DateRoadTheme.colors.white,
            dragHandle = null
        ) {
            Column(
                modifier = modifier
            ) {
                content()
                DateRoadBasicButton(
                    isEnabled = true,
                    textContent = buttonText,
                    onClick = {
                        onButtonClick()
                        coroutineScope.launch { sheetState.hide() }
                    },
                    enabledBackgroundColor = (
                        if (isButtonEnabled) {
                            DateRoadTheme.colors.purple600
                        } else {
                            DateRoadTheme.colors.gray200
                        }
                        ),
                    enabledTextColor = (
                        if (isButtonEnabled) {
                            DateRoadTheme.colors.white
                        } else {
                            (DateRoadTheme.colors.gray400)
                        }
                        )
                )
                Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DateRoadBottomSheetPreview() {
    DATEROADTheme {
        var isBottomSheetOpen by rememberSaveable { mutableStateOf(false) }

        Button(onClick = { isBottomSheetOpen = true }) {
            Text(
                text = "BottomSheet",
                color = DateRoadTheme.colors.black,
                style = DateRoadTheme.typography.titleExtra24
            )
        }

        DateRoadBottomSheet(
            modifier = Modifier.padding(top = 20.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
            isBottomSheetOpen = isBottomSheetOpen,
            onDismissRequest = { isBottomSheetOpen = !isBottomSheetOpen },
            isButtonEnabled = false,
            buttonText = "test",
            content = {
                Text(
                    text = "BottomSheet",
                    color = DateRoadTheme.colors.black,
                    style = DateRoadTheme.typography.titleExtra24
                )
            }
        )
    }
}
