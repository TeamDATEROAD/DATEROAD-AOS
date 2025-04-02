package org.sopt.teamdateroad.presentation.ui.pointhistory

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.sopt.teamdateroad.domain.usecase.GetPointHistoryUseCase
import org.sopt.teamdateroad.domain.usecase.GetUserPointUseCase
import org.sopt.teamdateroad.domain.usecase.PostAdsPointUseCase
import org.sopt.teamdateroad.presentation.util.base.BaseViewModel
import org.sopt.teamdateroad.presentation.util.view.LoadState
import javax.inject.Inject

@HiltViewModel
class PointHistoryViewModel @Inject constructor(
    private val getPointHistoryUseCase: GetPointHistoryUseCase,
    private val getUserPointUseCase: GetUserPointUseCase,
    private val postAdsPointUseCase: PostAdsPointUseCase,
) : BaseViewModel<PointHistoryContract.PointHistoryUiState, PointHistoryContract.PointHistorySideEffect, PointHistoryContract.PointHistoryEvent>() {
    override fun createInitialState(): PointHistoryContract.PointHistoryUiState =
        PointHistoryContract.PointHistoryUiState()

    override suspend fun handleEvent(event: PointHistoryContract.PointHistoryEvent) {
        when (event) {
            is PointHistoryContract.PointHistoryEvent.FetchPointHistory -> setState { copy(loadState = event.loadState, pointHistory = event.pointHistory) }
            is PointHistoryContract.PointHistoryEvent.OnTabBarClicked -> setState { copy(pointHistoryTabType = event.pointHistoryTabType) }
            is PointHistoryContract.PointHistoryEvent.FetchUserPoint -> setState { copy(loadState = event.loadState, userPoint = event.userPoint) }
            PointHistoryContract.PointHistoryEvent.OnPointCollectBottomSheetClick -> setState { copy(isPointCollectBottomSheetOpen = true) }
            PointHistoryContract.PointHistoryEvent.OnPointCollectBottomSheetDismiss -> setState { copy(isPointCollectBottomSheetOpen = false) }
            PointHistoryContract.PointHistoryEvent.FailLoadAdsPoint -> setState { copy(loadState = LoadState.Loading) }
            PointHistoryContract.PointHistoryEvent.DismissFullAdsDialog -> setState { copy(isFullAdsDialogOpen = false) }
            PointHistoryContract.PointHistoryEvent.FullAds -> setState { copy(isFullAdsDialogOpen = true) }
        }
    }

    fun fetchPointHistory() {
        viewModelScope.launch {
            setEvent(
                PointHistoryContract.PointHistoryEvent.FetchPointHistory(loadState = LoadState.Loading, pointHistory = currentState.pointHistory)
            )
            getPointHistoryUseCase().onSuccess { pointHistory ->
                setEvent(
                    PointHistoryContract.PointHistoryEvent.FetchPointHistory(loadState = LoadState.Success, pointHistory = pointHistory)
                )
            }.onFailure {
                setEvent(
                    PointHistoryContract.PointHistoryEvent.FetchPointHistory(loadState = LoadState.Error, pointHistory = currentState.pointHistory)
                )
            }
        }
    }

    fun fetchUserPoint() {
        viewModelScope.launch {
            setEvent(PointHistoryContract.PointHistoryEvent.FetchUserPoint(loadState = LoadState.Loading, userPoint = currentState.userPoint))
            getUserPointUseCase().onSuccess { userPoint ->
                setEvent(PointHistoryContract.PointHistoryEvent.FetchUserPoint(loadState = LoadState.Success, userPoint = userPoint))
            }.onFailure {
                setEvent(PointHistoryContract.PointHistoryEvent.FetchUserPoint(loadState = LoadState.Error, userPoint = currentState.userPoint))
            }
        }
    }

    fun postAdsPoint() {
        viewModelScope.launch {
            postAdsPointUseCase().onSuccess {
                fetchPointHistory()
                fetchUserPoint()
            }.onFailure {
                setState { copy(loadState = LoadState.Loading) }
            }
        }
    }
}
