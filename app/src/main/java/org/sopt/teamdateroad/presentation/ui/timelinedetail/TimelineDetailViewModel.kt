package org.sopt.teamdateroad.presentation.ui.timelinedetail

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.share.WebSharerClient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import org.sopt.teamdateroad.domain.model.TimelineDetail
import org.sopt.teamdateroad.domain.usecase.DeleteTimelineUseCase
import org.sopt.teamdateroad.domain.usecase.GetNicknameUseCase
import org.sopt.teamdateroad.domain.usecase.GetTimelineDetailUseCase
import org.sopt.teamdateroad.presentation.util.ShareKakao.START_AT
import org.sopt.teamdateroad.presentation.util.ShareKakao.TEMPLATE_ID
import org.sopt.teamdateroad.presentation.util.ShareKakao.USER_NAME
import org.sopt.teamdateroad.presentation.util.base.BaseViewModel
import org.sopt.teamdateroad.presentation.util.view.LoadState

@HiltViewModel
class TimelineDetailViewModel @Inject constructor(
    private val deleteTimelineUseCase: DeleteTimelineUseCase,
    private val getTimelineDetailUseCase: GetTimelineDetailUseCase,
    private val getNickNameUseCase: GetNicknameUseCase
) : BaseViewModel<TimelineDetailContract.TimelineDetailUiState, TimelineDetailContract.TimelineDetailSideEffect, TimelineDetailContract.TimelineDetailEvent>() {
    override fun createInitialState(): TimelineDetailContract.TimelineDetailUiState = TimelineDetailContract.TimelineDetailUiState()

    override suspend fun handleEvent(event: TimelineDetailContract.TimelineDetailEvent) {
        when (event) {
            is TimelineDetailContract.TimelineDetailEvent.SetTimelineDetail -> setState { copy(loadState = event.loadState, timelineDetail = event.timelineDetail) }
            is TimelineDetailContract.TimelineDetailEvent.SetShowDeleteBottomSheet -> setState { copy(showDeleteBottomSheet = event.showDeleteBottomSheet) }
            is TimelineDetailContract.TimelineDetailEvent.SetShowDeleteDialog -> setState { copy(showDeleteDialog = event.showDeleteDialog) }
            is TimelineDetailContract.TimelineDetailEvent.SetShowKakaoDialog -> setState { copy(showKakaoDialog = event.showKakaoDialog) }
            is TimelineDetailContract.TimelineDetailEvent.DeleteTimeline -> setState { copy(deleteLoadState = event.deleteLoadState) }
            is TimelineDetailContract.TimelineDetailEvent.SetLoadState -> setState { copy(loadState = event.loadState) }
            is TimelineDetailContract.TimelineDetailEvent.SetSideEffect -> setSideEffect(event.sideEffect)
            is TimelineDetailContract.TimelineDetailEvent.ShareKakao -> shareKakao(event.context, event.timelineDetail)
        }
    }

    fun fetchTimelineDetail(timelineId: Int) {
        viewModelScope.launch {
            setEvent(TimelineDetailContract.TimelineDetailEvent.SetTimelineDetail(loadState = LoadState.Loading, timelineDetail = currentState.timelineDetail))
            getTimelineDetailUseCase(timelineId).onSuccess { timelineDetail ->
                setEvent(TimelineDetailContract.TimelineDetailEvent.SetTimelineDetail(loadState = LoadState.Success, timelineDetail = timelineDetail))
            }.onFailure {
                setEvent(TimelineDetailContract.TimelineDetailEvent.SetTimelineDetail(loadState = LoadState.Error, timelineDetail = currentState.timelineDetail))
            }
        }
    }

    fun deleteTimeline(timelineId: Int) {
        viewModelScope.launch {
            setEvent(TimelineDetailContract.TimelineDetailEvent.DeleteTimeline(deleteLoadState = LoadState.Loading))
            deleteTimelineUseCase(timelineId).onSuccess {
                setEvent(TimelineDetailContract.TimelineDetailEvent.DeleteTimeline(deleteLoadState = LoadState.Success))
            }.onFailure {
                setEvent(TimelineDetailContract.TimelineDetailEvent.DeleteTimeline(deleteLoadState = LoadState.Error))
            }
        }
    }

    fun shareKakao(context: Context, timelineDetail: TimelineDetail) {
        val templateId = 109999
        val templateArgs = mutableMapOf<String, String>()

        templateArgs[USER_NAME] = getNickNameUseCase()
        templateArgs[START_AT] = timelineDetail.startAt

        timelineDetail.places.forEachIndexed { index, place ->
            if (index < 5) {
                templateArgs["name${index + 1}"] = place.title
                templateArgs["duration${index + 1}"] = place.duration
            }
        }

        if (ShareClient.instance.isKakaoTalkSharingAvailable(context)) {
            ShareClient.instance.shareCustom(context, TEMPLATE_ID, templateArgs) { sharingResult, error ->
                if (sharingResult != null) {
                    context.startActivity(sharingResult.intent)
                }
            }
        } else {
            val sharerUrl = WebSharerClient.instance.makeCustomUrl(TEMPLATE_ID, templateArgs)
            try {
                KakaoCustomTabsClient.openWithDefault(context, sharerUrl)
            } catch (e: UnsupportedOperationException) {
                KakaoCustomTabsClient.open(context, sharerUrl)
            }
        }
    }
}
