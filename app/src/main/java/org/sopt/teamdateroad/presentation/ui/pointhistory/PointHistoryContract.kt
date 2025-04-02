package org.sopt.teamdateroad.presentation.ui.pointhistory

import org.sopt.teamdateroad.domain.model.PointHistory
import org.sopt.teamdateroad.domain.model.UserPoint
import org.sopt.teamdateroad.presentation.type.EnrollType
import org.sopt.teamdateroad.presentation.type.PointHistoryTabType
import org.sopt.teamdateroad.presentation.ui.coursedetail.CourseDetailContract.CourseDetailEvent
import org.sopt.teamdateroad.presentation.util.base.UiEvent
import org.sopt.teamdateroad.presentation.util.base.UiSideEffect
import org.sopt.teamdateroad.presentation.util.base.UiState
import org.sopt.teamdateroad.presentation.util.view.LoadState

class PointHistoryContract {
    data class PointHistoryUiState(
        val loadState: LoadState = LoadState.Idle,
        val userPoint: UserPoint = UserPoint(),
        val isPointCollectBottomSheetOpen: Boolean = false,
        val pointHistoryTabType: PointHistoryTabType = PointHistoryTabType.GAINED_HISTORY,
        val pointHistory: PointHistory = PointHistory(),
        val isFullAdsDialogOpen: Boolean = false,
    ) : UiState

    sealed interface PointHistorySideEffect : UiSideEffect {
        data object PopBackStack : PointHistorySideEffect
        data object NavigateToAds : PointHistorySideEffect
        data class NavigateToEnroll(val enrollType: EnrollType, val viewPath: String, val id: Int?) : PointHistorySideEffect
    }

    sealed class PointHistoryEvent : UiEvent {
        data class FetchPointHistory(val loadState: LoadState, val pointHistory: PointHistory) : PointHistoryEvent()
        data class FetchUserPoint(val loadState: LoadState, val userPoint: UserPoint) : PointHistoryEvent()
        data class OnTabBarClicked(val pointHistoryTabType: PointHistoryTabType) : PointHistoryEvent()
        data object FailLoadAdsPoint : PointHistoryEvent()
        data object OnPointCollectBottomSheetClick : PointHistoryEvent()
        data object OnPointCollectBottomSheetDismiss : PointHistoryEvent()
        data object FullAds : PointHistoryEvent()
        data object DismissFullAdsDialog : PointHistoryEvent()
    }
}
