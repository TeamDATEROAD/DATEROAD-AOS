package org.sopt.teamdateroad.presentation.ui.pointhistory

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import org.sopt.teamdateroad.BuildConfig
import org.sopt.teamdateroad.R
import org.sopt.teamdateroad.domain.model.PointHistory
import org.sopt.teamdateroad.domain.model.UserPoint
import org.sopt.teamdateroad.presentation.type.EmptyViewType
import org.sopt.teamdateroad.presentation.type.EnrollType
import org.sopt.teamdateroad.presentation.type.OneButtonDialogWithDescriptionType
import org.sopt.teamdateroad.presentation.type.PointHistoryTabType
import org.sopt.teamdateroad.presentation.ui.component.bottomsheet.DateRoadPointBottomSheet
import org.sopt.teamdateroad.presentation.ui.component.bottomsheet.model.collect.DateRoadCollectPointType
import org.sopt.teamdateroad.presentation.ui.component.dialog.DateRoadOneButtonDialogWithDescription
import org.sopt.teamdateroad.presentation.ui.component.tabbar.DateRoadTabBar
import org.sopt.teamdateroad.presentation.ui.component.tabbar.DateRoadTabTitle
import org.sopt.teamdateroad.presentation.ui.component.topbar.DateRoadBasicTopBar
import org.sopt.teamdateroad.presentation.ui.component.view.DateRoadErrorView
import org.sopt.teamdateroad.presentation.ui.component.view.DateRoadIdleView
import org.sopt.teamdateroad.presentation.ui.component.view.DateRoadLoadingView
import org.sopt.teamdateroad.presentation.ui.pointhistory.component.PointHistoryCard
import org.sopt.teamdateroad.presentation.ui.pointhistory.component.PointHistoryPointBox
import org.sopt.teamdateroad.presentation.util.AdsAmplitude
import org.sopt.teamdateroad.presentation.util.ViewPath.POINT_HISTORY
import org.sopt.teamdateroad.presentation.util.amplitude.AmplitudeUtils
import org.sopt.teamdateroad.presentation.util.view.LoadState
import org.sopt.teamdateroad.ui.theme.DATEROADTheme
import org.sopt.teamdateroad.ui.theme.DateRoadTheme

@Composable
fun PointHistoryRoute(
    padding: PaddingValues,
    viewModel: PointHistoryViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
    navigateToEnroll: (EnrollType, String, Int?) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val activity = context as? Activity
    val adRequest = remember { AdRequest.Builder().build() }

    LaunchedEffect(Unit) {
        viewModel.fetchPointHistory()
        viewModel.fetchUserPoint()
        AmplitudeUtils.trackEvent(eventName = AdsAmplitude.VIEW_POINT)
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .collect { pointHistorySideEffect ->
                when (pointHistorySideEffect) {
                    is PointHistoryContract.PointHistorySideEffect.PopBackStack -> popBackStack()
                    is PointHistoryContract.PointHistorySideEffect.NavigateToEnroll -> navigateToEnroll(
                        pointHistorySideEffect.enrollType,
                        pointHistorySideEffect.viewPath,
                        pointHistorySideEffect.id
                    )

                    PointHistoryContract.PointHistorySideEffect.NavigateToAds -> {
                        RewardedAd.load(
                            context,
                            BuildConfig.GOOGLE_ADS_API_ID,
                            adRequest,
                            object : RewardedAdLoadCallback() {
                                override fun onAdLoaded(ad: RewardedAd) {
                                    activity?.let {
                                        ad.show(it) { rewardItem ->
                                            viewModel.postAdsPoint()
                                        }
                                    }
                                }

                                override fun onAdFailedToLoad(error: LoadAdError) {
                                    when (error.code) {
                                        AdRequest.ERROR_CODE_NO_FILL -> viewModel.setEvent(PointHistoryContract.PointHistoryEvent.FullAds)

                                        else -> viewModel.setEvent(PointHistoryContract.PointHistoryEvent.FailLoadAdsPoint)
                                    }
                                }
                            }
                        )
                    }
                }
            }
    }

    when (uiState.loadState) {
        LoadState.Idle -> DateRoadIdleView()

        LoadState.Loading -> DateRoadLoadingView()

        LoadState.Success -> {
            PointHistoryScreen(
                padding = padding,
                pointHistoryUiState = uiState,
                onTabBarClicked = { pointHistoryTabType ->
                    viewModel.setEvent(
                        PointHistoryContract.PointHistoryEvent.OnTabBarClicked(pointHistoryTabType)
                    )
                },
                onTopBarIconClicked = { viewModel.setSideEffect(PointHistoryContract.PointHistorySideEffect.PopBackStack) },
                onClickCollectPoint = {
                    viewModel.setEvent(PointHistoryContract.PointHistoryEvent.OnPointCollectBottomSheetClick)
                },
                onDisMissCollectPoint = {
                    AmplitudeUtils.trackEvent(eventName = AdsAmplitude.CLICK_COLLECT_POINT_CLOSE)
                    viewModel.setEvent(PointHistoryContract.PointHistoryEvent.OnPointCollectBottomSheetDismiss)
                },
                onSelectEnroll = {
                    AmplitudeUtils.trackEvent(eventName = AdsAmplitude.CLICK_COURSE)
                    viewModel.setEvent(PointHistoryContract.PointHistoryEvent.OnPointCollectBottomSheetDismiss)
                    viewModel.setSideEffect(
                        PointHistoryContract.PointHistorySideEffect.NavigateToEnroll(
                            enrollType = EnrollType.COURSE,
                            viewPath = POINT_HISTORY,
                            id = null
                        )
                    )
                },
                onSelectAds = {
                    AmplitudeUtils.trackEvent(eventName = AdsAmplitude.CLICK_AD)
                    viewModel.setEvent(PointHistoryContract.PointHistoryEvent.OnPointCollectBottomSheetDismiss)
                    viewModel.setSideEffect(PointHistoryContract.PointHistorySideEffect.NavigateToAds)
                },
                onDismissFullAdsDialog = {
                    viewModel.setEvent(PointHistoryContract.PointHistoryEvent.DismissFullAdsDialog)
                }
            )
        }

        LoadState.Error -> DateRoadErrorView()
    }
}

@Composable
fun PointHistoryScreen(
    padding: PaddingValues,
    pointHistoryUiState: PointHistoryContract.PointHistoryUiState = PointHistoryContract.PointHistoryUiState(),
    onTabBarClicked: (PointHistoryTabType) -> Unit,
    onTopBarIconClicked: () -> Unit,
    onClickCollectPoint: () -> Unit,
    onDisMissCollectPoint: () -> Unit,
    onSelectEnroll: () -> Unit,
    onSelectAds: () -> Unit,
    onDismissFullAdsDialog: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(paddingValues = padding)
            .background(color = DateRoadTheme.colors.white)
            .fillMaxSize()
    ) {
        DateRoadBasicTopBar(
            title = stringResource(id = R.string.top_bar_title_point_history),
            leftIconResource = R.drawable.ic_top_bar_back_white,
            backGroundColor = DateRoadTheme.colors.white,
            onLeftIconClick = onTopBarIconClicked
        )
        Spacer(modifier = Modifier.height(16.dp))
        PointHistoryPointBox(
            modifier = Modifier.padding(horizontal = 16.dp),
            userPoint = pointHistoryUiState.userPoint,
            onClickCollectPoint = onClickCollectPoint
        )
        Spacer(modifier = Modifier.height(16.dp))
        DateRoadTabBar(
            selectedTabPosition = pointHistoryUiState.pointHistoryTabType.position
        ) {
            PointHistoryTabType.entries.forEachIndexed { index, pointHistoryTabType ->
                DateRoadTabTitle(
                    title = stringResource(id = pointHistoryTabType.titleRes),
                    selected = index == pointHistoryUiState.pointHistoryTabType.position,
                    position = index,
                    onClick = {
                        onTabBarClicked(pointHistoryTabType)
                    }
                )
            }
        }
        val pointHistory = when (pointHistoryUiState.pointHistoryTabType) {
            PointHistoryTabType.GAINED_HISTORY -> pointHistoryUiState.pointHistory.gained
            PointHistoryTabType.USED_HISTORY -> pointHistoryUiState.pointHistory.used
        }
        if (pointHistory.isEmpty()) {
            val emptyViewType = when (pointHistoryUiState.pointHistoryTabType) {
                PointHistoryTabType.USED_HISTORY -> EmptyViewType.POINT_HISTORY_USED_HISTORY
                PointHistoryTabType.GAINED_HISTORY -> EmptyViewType.POINT_HISTORY_GAINED_HISTORY
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth(),
                    painter = painterResource(id = emptyViewType.imageRes),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth
                )
                Spacer(modifier = Modifier.height(57.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    text = stringResource(id = emptyViewType.titleRes),
                    color = DateRoadTheme.colors.gray300,
                    style = DateRoadTheme.typography.titleBold18,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        } else {
            LazyColumn {
                items(pointHistory.size) { index ->
                    PointHistoryCard(point = pointHistory[index])
                    HorizontalDivider(
                        color = DateRoadTheme.colors.gray100,
                        thickness = 1.dp
                    )
                }
            }
        }
    }

    if (pointHistoryUiState.isFullAdsDialogOpen) {
        DateRoadOneButtonDialogWithDescription(
            oneButtonDialogWithDescriptionType = OneButtonDialogWithDescriptionType.FULL_ADS,
            onDismissRequest = onDismissFullAdsDialog,
            onClickConfirm = onDismissFullAdsDialog
        )
    }

    DateRoadPointBottomSheet(
        isBottomSheetOpen = pointHistoryUiState.isPointCollectBottomSheetOpen,
        title = stringResource(R.string.point_box_get_point_button_text),
        onClick = { dateRoadCollectPointType ->
            when (dateRoadCollectPointType) {
                DateRoadCollectPointType.WATCH_ADS -> onSelectAds()
                DateRoadCollectPointType.COURSE_REGISTRATION -> onSelectEnroll()
            }
        },
        onDismissRequest = onDisMissCollectPoint
    )
}

@Preview
@Composable
fun PointHistoryPreview() {
    DATEROADTheme {
        PointHistoryScreen(
            padding = PaddingValues(0.dp),
            pointHistoryUiState = PointHistoryContract.PointHistoryUiState(
                userPoint = UserPoint(),
                loadState = LoadState.Success,
                pointHistory = PointHistory(
                    gained = listOf(
//                        Point(point = "+150", description = "서버의 바다여행", createdAt = "2023.12.31"),
//                        Point(point = "+150", description = "서버의 바다여행", createdAt = "2023.12.31"),
//                        Point(point = "+150", description = "서버의 바다여행", createdAt = "2023.12.31")
                    ),
                    used = listOf()
                )
            ),
            onTabBarClicked = {},
            onTopBarIconClicked = {},
            onClickCollectPoint = {},
            onDisMissCollectPoint = {},
            onSelectEnroll = {},
            onSelectAds = {},
            onDismissFullAdsDialog = {}
        )
    }
}
