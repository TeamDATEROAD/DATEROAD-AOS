package org.sopt.teamdateroad.presentation.ui.look

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import org.sopt.teamdateroad.R
import org.sopt.teamdateroad.domain.type.GyeonggiAreaType
import org.sopt.teamdateroad.domain.type.IncheonAreaType
import org.sopt.teamdateroad.domain.type.MoneyTagType
import org.sopt.teamdateroad.domain.type.RegionType
import org.sopt.teamdateroad.domain.type.SeoulAreaType
import org.sopt.teamdateroad.presentation.type.ChipType
import org.sopt.teamdateroad.presentation.type.EmptyViewType
import org.sopt.teamdateroad.presentation.type.EnrollType
import org.sopt.teamdateroad.presentation.ui.component.bottomsheet.DateRoadRegionBottomSheet
import org.sopt.teamdateroad.presentation.ui.component.button.DateRoadAreaButton
import org.sopt.teamdateroad.presentation.ui.component.button.DateRoadImageButton
import org.sopt.teamdateroad.presentation.ui.component.chip.DateRoadTextChip
import org.sopt.teamdateroad.presentation.ui.component.topbar.DateRoadLeftTitleTopBar
import org.sopt.teamdateroad.presentation.ui.component.view.DateRoadEmptyView
import org.sopt.teamdateroad.presentation.ui.component.view.DateRoadErrorView
import org.sopt.teamdateroad.presentation.ui.component.view.DateRoadLoadingView
import org.sopt.teamdateroad.presentation.ui.look.component.LookCourseCard
import org.sopt.teamdateroad.presentation.util.Default
import org.sopt.teamdateroad.presentation.util.ViewPath.LOOK
import org.sopt.teamdateroad.presentation.util.modifier.noRippleClickable
import org.sopt.teamdateroad.presentation.util.view.LoadState
import org.sopt.teamdateroad.ui.theme.DATEROADTheme
import org.sopt.teamdateroad.ui.theme.DateRoadTheme

@Composable
fun LookRoute(
    padding: PaddingValues,
    viewModel: LookViewModel = hiltViewModel(),
    navigateToEnroll: (EnrollType, String, Int?) -> Unit,
    navigateToCourseDetail: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(uiState.area, uiState.money) {
        viewModel.fetchFilteredCourses(
            country = uiState.region,
            city = when (uiState.area) {
                is SeoulAreaType -> (uiState.area as SeoulAreaType)
                is GyeonggiAreaType -> (uiState.area as GyeonggiAreaType)
                is IncheonAreaType -> (uiState.area as IncheonAreaType)
                else -> null
            },
            cost = uiState.money
        )
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .collect { lookSideEffect ->
                when (lookSideEffect) {
                    is LookContract.LookSideEffect.NavigateToCourseDetail -> navigateToCourseDetail(lookSideEffect.courseId)
                    is LookContract.LookSideEffect.NavigateToEnroll -> navigateToEnroll(EnrollType.COURSE, LOOK, null)
                }
            }
    }

    when (uiState.loadState) {
        LoadState.Idle -> DateRoadLoadingView()

        LoadState.Loading -> DateRoadLoadingView()

        LoadState.Success -> {
            LookScreen(
                padding = padding,
                lookUiState = uiState,
                onAreaButtonClicked = {
                    viewModel.setEvent(
                        LookContract.LookEvent.OnAreaButtonClicked
                    )
                },
                onResetButtonClicked = {
                    viewModel.setEvent(
                        LookContract.LookEvent.OnResetButtonClicked
                    )
                },
                onRegionBottomSheetDismissRequest = {
                    viewModel.setEvent(
                        LookContract.LookEvent.OnRegionBottomSheetDismissRequest
                    )
                },
                onMoneyChipClicked = { moneyTagType ->
                    viewModel.setEvent(
                        LookContract.LookEvent.OnMoneyChipClicked(money = moneyTagType)
                    )
                },
                onRegionBottomSheetButtonClicked = { region: RegionType?, area: Any? ->
                    viewModel.setEvent(
                        LookContract.LookEvent.OnRegionBottomSheetButtonClicked(region = region, area = area)
                    )
                },
                onRegionBottomSheetRegionClicked = { region: RegionType? ->
                    viewModel.setEvent(
                        LookContract.LookEvent.OnRegionBottomSheetRegionClicked(region = region)
                    )
                },
                onRegionBottomSheetAreaClicked = { area: Any? ->
                    viewModel.setEvent(
                        LookContract.LookEvent.OnRegionBottomSheetAreaClicked(area = area)
                    )
                },
                onEnrollButtonClicked = { viewModel.setSideEffect(LookContract.LookSideEffect.NavigateToEnroll) },
                onCourseCardClicked = { courseId -> viewModel.setSideEffect(LookContract.LookSideEffect.NavigateToCourseDetail(courseId = courseId)) }
            )
        }

        LoadState.Error -> DateRoadErrorView()
    }
}

@Composable
fun LookScreen(
    padding: PaddingValues,
    lookUiState: LookContract.LookUiState = LookContract.LookUiState(),
    onAreaButtonClicked: () -> Unit = {},
    onResetButtonClicked: () -> Unit = {},
    onRegionBottomSheetDismissRequest: () -> Unit = {},
    onMoneyChipClicked: (MoneyTagType?) -> Unit = {},
    onRegionBottomSheetButtonClicked: (RegionType?, Any?) -> Unit = { _, _ -> },
    onRegionBottomSheetRegionClicked: (RegionType?) -> Unit = {},
    onRegionBottomSheetAreaClicked: (Any?) -> Unit = {},
    onEnrollButtonClicked: () -> Unit = {},
    onCourseCardClicked: (Int) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .background(color = DateRoadTheme.colors.white)
            .padding(padding)
            .fillMaxSize()
    ) {
        DateRoadLeftTitleTopBar(
            title = stringResource(id = R.string.top_bar_title_look),
            buttonContent = {
                DateRoadImageButton(
                    isEnabled = true,
                    onClick = onEnrollButtonClicked,
                    cornerRadius = 14.dp,
                    paddingHorizontal = 16.dp,
                    paddingVertical = 9.dp
                )
            }
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            DateRoadAreaButton(
                modifier = Modifier.weight(1f),
                isSelected = lookUiState.area != null,
                textContent = when (lookUiState.area) {
                    is SeoulAreaType -> lookUiState.area.title
                    is GyeonggiAreaType -> lookUiState.area.title
                    is IncheonAreaType -> lookUiState.area.title
                    else -> Default.REGION
                },
                onClick = onAreaButtonClicked
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier
                    .padding(8.dp)
                    .noRippleClickable(onClick = onResetButtonClicked),
                painter = painterResource(id = R.drawable.ic_all_reset),
                contentDescription = null,
                tint = DateRoadTheme.colors.gray300
            )
        }
        LazyRow(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(MoneyTagType.entries.size) { index ->
                DateRoadTextChip(
                    text = MoneyTagType.entries[index].title,
                    chipType = ChipType.MONEY,
                    isSelected = lookUiState.money == MoneyTagType.entries[index],
                    onSelectedChange = {
                        onMoneyChipClicked(MoneyTagType.entries[index])
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        if (lookUiState.courses.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                DateRoadEmptyView(
                    emptyViewType = EmptyViewType.LOOK
                )
            }
        }
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(lookUiState.courses.size) { index ->
                LookCourseCard(course = lookUiState.courses[index], onClick = { onCourseCardClicked(lookUiState.courses[index].courseId) })
            }
        }
    }

    DateRoadRegionBottomSheet(
        isBottomSheetOpen = lookUiState.isRegionBottomSheetOpen,
        isButtonEnabled = lookUiState.regionBottomSheetSelectedRegion != null && lookUiState.regionBottomSheetSelectedArea != null,
        selectedRegion = lookUiState.regionBottomSheetSelectedRegion,
        onSelectedRegionChanged = { region -> onRegionBottomSheetRegionClicked(region) },
        selectedArea = lookUiState.regionBottomSheetSelectedArea,
        onSelectedAreaChanged = { area -> onRegionBottomSheetAreaClicked(area) },
        onButtonClick = { region, area ->
            onRegionBottomSheetButtonClicked(region, area)
        },
        onDismissRequest = onRegionBottomSheetDismissRequest
    )
}

@Preview()
@Composable
fun LookScreenPreview() {
    DATEROADTheme {
        LookScreen(padding = PaddingValues(0.dp))
    }
}
