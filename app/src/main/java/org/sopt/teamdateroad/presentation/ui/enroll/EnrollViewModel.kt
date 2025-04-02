package org.sopt.teamdateroad.presentation.ui.enroll

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import org.sopt.teamdateroad.data.dataremote.util.Date.NEAREST_DATE_START_OUTPUT_FORMAT
import org.sopt.teamdateroad.data.mapper.toEntity.toEnroll
import org.sopt.teamdateroad.domain.type.RegionType
import org.sopt.teamdateroad.domain.usecase.GetCourseDetailUseCase
import org.sopt.teamdateroad.domain.usecase.GetPlaceSearchResultUseCase
import org.sopt.teamdateroad.domain.usecase.GetTimelineDetailUseCase
import org.sopt.teamdateroad.domain.usecase.PostCourseUseCase
import org.sopt.teamdateroad.domain.usecase.PostTimelineUseCase
import org.sopt.teamdateroad.presentation.type.EnrollScreenType
import org.sopt.teamdateroad.presentation.type.EnrollType
import org.sopt.teamdateroad.presentation.util.UserPropertyAmplitude.DATE_SCHEDULE_NUM
import org.sopt.teamdateroad.presentation.util.UserPropertyAmplitude.USER_COURSE_COUNT
import org.sopt.teamdateroad.presentation.util.UserPropertyAmplitude.USER_POINT
import org.sopt.teamdateroad.presentation.util.amplitude.AmplitudeUtils
import org.sopt.teamdateroad.presentation.util.base.BaseViewModel
import org.sopt.teamdateroad.presentation.util.paging.PlaceSearchPagingSource
import org.sopt.teamdateroad.presentation.util.paging.PlaceSearchPagingSource.Companion.PAGE_SIZE
import org.sopt.teamdateroad.presentation.util.view.LoadState

@HiltViewModel
class EnrollViewModel @Inject constructor(
    private val getCourseDetailUseCase: GetCourseDetailUseCase,
    private val getTimelineDetailUseCase: GetTimelineDetailUseCase,
    private val postCourseUseCase: PostCourseUseCase,
    private val postTimelineUseCase: PostTimelineUseCase,
    private val getPlaceSearchResultUseCase: GetPlaceSearchResultUseCase
) : BaseViewModel<EnrollContract.EnrollUiState, EnrollContract.EnrollSideEffect, EnrollContract.EnrollEvent>() {
    override fun createInitialState(): EnrollContract.EnrollUiState = EnrollContract.EnrollUiState()

    override suspend fun handleEvent(event: EnrollContract.EnrollEvent) {
        when (event) {
            is EnrollContract.EnrollEvent.OnTopBarBackButtonClick -> {
                when (currentState.enrollType) {
                    EnrollType.COURSE -> {
                        when (currentState.page) {
                            EnrollScreenType.FIRST -> setSideEffect(EnrollContract.EnrollSideEffect.PopBackStack)
                            EnrollScreenType.SECOND -> setState { copy(page = EnrollScreenType.FIRST) }
                            EnrollScreenType.THIRD -> setState { copy(page = EnrollScreenType.SECOND) }
                        }
                    }

                    EnrollType.TIMELINE -> {
                        when (currentState.page) {
                            EnrollScreenType.FIRST -> setSideEffect(EnrollContract.EnrollSideEffect.PopBackStack)
                            EnrollScreenType.SECOND -> setState { copy(page = EnrollScreenType.FIRST) }
                            EnrollScreenType.THIRD -> Unit
                        }
                    }
                }
            }

            is EnrollContract.EnrollEvent.OnEnrollButtonClick -> {
                when (currentState.enrollType) {
                    EnrollType.COURSE -> {
                        when (currentState.page) {
                            EnrollScreenType.FIRST -> setState { copy(page = EnrollScreenType.SECOND) }
                            EnrollScreenType.SECOND -> setState { copy(page = EnrollScreenType.THIRD) }
                            EnrollScreenType.THIRD -> postCourse()
                        }
                    }

                    EnrollType.TIMELINE -> {
                        when (currentState.page) {
                            EnrollScreenType.FIRST -> setState { copy(page = EnrollScreenType.SECOND) }
                            EnrollScreenType.SECOND -> postTimeline()
                            EnrollScreenType.THIRD -> Unit
                        }
                    }
                }
            }

            is EnrollContract.EnrollEvent.OnDateTextFieldClick -> setState { copy(isDatePickerBottomSheetOpen = true) }
            is EnrollContract.EnrollEvent.OnTimeTextFieldClick -> setState { copy(isTimePickerBottomSheetOpen = true) }
            is EnrollContract.EnrollEvent.OnRegionTextFieldClick -> setState { copy(isRegionBottomSheetOpen = true, onRegionBottomSheetRegionSelected = RegionType.SEOUL, onRegionBottomSheetAreaSelected = null) }
            is EnrollContract.EnrollEvent.OnPlaceSearchButtonClick -> setState { copy(isPlaceSearchBottomSheetOpen = true) }
            is EnrollContract.EnrollEvent.OnKeywordChanged -> {
                setState {
                    copy(keyword = event.keyword)
                }
                getPlaceSearchResult()
            }

            is EnrollContract.EnrollEvent.OnPlaceSearchBottomSheetDismiss -> setState { copy(isPlaceSearchBottomSheetOpen = false, keyword = "", searchedPlaceInfos = emptyFlow()) }
            is EnrollContract.EnrollEvent.OnSelectedPlaceCourseTimeClick -> setState { copy(isDurationBottomSheetOpen = true) }
            is EnrollContract.EnrollEvent.OnDatePickerBottomSheetDismissRequest -> setState { copy(isDatePickerBottomSheetOpen = false) }
            is EnrollContract.EnrollEvent.OnTimePickerBottomSheetDismissRequest -> setState { copy(isTimePickerBottomSheetOpen = false) }
            is EnrollContract.EnrollEvent.OnRegionBottomSheetDismissRequest -> setState { copy(isRegionBottomSheetOpen = false) }
            is EnrollContract.EnrollEvent.OnDurationBottomSheetDismissRequest -> setState { copy(isDurationBottomSheetOpen = false) }
            is EnrollContract.EnrollEvent.FetchEnrollCourseType -> setState { copy(enrollType = event.enrollType) }
            is EnrollContract.EnrollEvent.FetchCourseDetail -> setState { copy(fetchEnrollState = event.fetchEnrollState, enroll = event.courseDetail?.toEnroll() ?: currentState.enroll) }
            is EnrollContract.EnrollEvent.FetchTimelineDetail -> setState { copy(fetchEnrollState = event.fetchEnrollState, enroll = event.timelineDetail?.toEnroll() ?: currentState.enroll) }
            is EnrollContract.EnrollEvent.SetEnrollButtonEnabled -> setState { copy(isEnrollButtonEnabled = event.isEnrollButtonEnabled) }
            is EnrollContract.EnrollEvent.SetImage -> setState { copy(enroll = currentState.enroll.copy(images = event.images), thumbnailIndex = 0) }
            is EnrollContract.EnrollEvent.OnImageDeleteButtonClick -> setState {
                copy(
                    enroll = currentState.enroll.copy(
                        images = currentState.enroll.images.toMutableList().apply { removeAt(event.index) }
                    ),
                    thumbnailIndex = if (event.moveThumbnail) (thumbnailIndex - 1).coerceAtLeast(0) else thumbnailIndex
                )
            }

            is EnrollContract.EnrollEvent.OnTitleValueChange -> setState { copy(enroll = currentState.enroll.copy(title = event.title)) }
            is EnrollContract.EnrollEvent.OnPlaceSelected -> {
                setState { copy(keyword = "", searchedPlaceInfos = emptyFlow(), place = currentState.place.copy(title = event.placeInfo.placeName, address = event.placeInfo.addressName), selectedPlaceInfos = currentState.selectedPlaceInfos + event.placeInfo, isPlaceSearchBottomSheetOpen = false) }
            }

            is EnrollContract.EnrollEvent.OnDatePickerBottomSheetButtonClick -> setState { copy(enroll = currentState.enroll.copy(date = event.date), isDatePickerBottomSheetOpen = false) }

            is EnrollContract.EnrollEvent.OnTimePickerBottomSheetButtonClick -> setState { copy(enroll = currentState.enroll.copy(startAt = event.startAt), isTimePickerBottomSheetOpen = false) }
            is EnrollContract.EnrollEvent.OnDateChipClicked -> setState {
                copy(
                    enroll = enroll.copy(
                        tags = currentState.enroll.tags.toMutableList().apply {
                            if (contains(event.tag)) {
                                remove(event.tag)
                            } else if (size < 3) {
                                add(event.tag)
                            }
                        }
                    )
                )
            }

            is EnrollContract.EnrollEvent.OnRegionBottomSheetRegionChipClick -> setState { copy(onRegionBottomSheetRegionSelected = event.country) }
            is EnrollContract.EnrollEvent.OnRegionBottomSheetAreaChipClick -> setState { copy(onRegionBottomSheetAreaSelected = event.city) }
            is EnrollContract.EnrollEvent.OnRegionBottomSheetButtonClick -> setState { copy(isRegionBottomSheetOpen = false, enroll = currentState.enroll.copy(country = event.region, city = event.area)) }
            is EnrollContract.EnrollEvent.OnAddPlaceButtonClick -> setState { copy(enroll = currentState.enroll.copy(places = currentState.enroll.places.toMutableList().apply { add(event.place) }), place = currentState.place.copy(title = "", duration = "")) }
            is EnrollContract.EnrollEvent.OnPlaceCardDragAndDrop -> setState { copy(enroll = currentState.enroll.copy(places = event.places)) }
            is EnrollContract.EnrollEvent.OnPlaceTitleValueChange -> setState { copy(place = currentState.place.copy(title = event.placeTitle)) }
            is EnrollContract.EnrollEvent.OnDurationBottomSheetButtonClick -> setState { copy(isDurationBottomSheetOpen = false, place = currentState.place.copy(duration = event.placeDuration)) }
            is EnrollContract.EnrollEvent.OnEditableValueChange -> setState { copy(isPlaceEditable = event.editable) }
            is EnrollContract.EnrollEvent.OnPlaceCardDeleteButtonClick -> setState { copy(enroll = currentState.enroll.copy(places = currentState.enroll.places.toMutableList().apply { removeAt(event.index) })) }
            is EnrollContract.EnrollEvent.OnDescriptionValueChange -> setState { copy(enroll = currentState.enroll.copy(description = event.description)) }
            is EnrollContract.EnrollEvent.OnCostValueChange -> setState { copy(enroll = currentState.enroll.copy(cost = event.cost)) }
            is EnrollContract.EnrollEvent.Enroll -> setState { copy(loadState = event.loadState, thumbnailIndex = 0) }
            is EnrollContract.EnrollEvent.OnSelectThumbnail -> setState { copy(thumbnailIndex = event.index) }
            is EnrollContract.EnrollEvent.SetTitleValidationState -> setState { copy(titleValidateState = event.titleValidationState) }
            is EnrollContract.EnrollEvent.SetDateValidationState -> setState { copy(dateValidateState = event.dateValidationState) }
            is EnrollContract.EnrollEvent.SetIsEnrollSuccessDialogOpen -> setState { copy(isEnrollSuccessDialogOpen = event.isEnrollSuccessDialogOpen) }
        }
    }

    fun fetchCourseDetail(courseId: Int) {
        viewModelScope.launch {
            setEvent(EnrollContract.EnrollEvent.FetchCourseDetail(fetchEnrollState = LoadState.Loading, courseDetail = null))
            getCourseDetailUseCase(courseId = courseId).onSuccess { courseDetail ->
                setEvent(EnrollContract.EnrollEvent.FetchCourseDetail(fetchEnrollState = LoadState.Success, courseDetail = courseDetail.copy(startAt = courseDetail.startAt.substringBefore(NEAREST_DATE_START_OUTPUT_FORMAT))))
            }.onFailure {
                setEvent(EnrollContract.EnrollEvent.FetchCourseDetail(fetchEnrollState = LoadState.Error, courseDetail = null))
            }
        }
    }

    fun fetchTimelineDetail(timelineId: Int) {
        viewModelScope.launch {
            setEvent(EnrollContract.EnrollEvent.FetchTimelineDetail(fetchEnrollState = LoadState.Loading, timelineDetail = null))
            getTimelineDetailUseCase(timelineId = timelineId).onSuccess { timelineDetail ->
                setEvent(EnrollContract.EnrollEvent.FetchTimelineDetail(fetchEnrollState = LoadState.Success, timelineDetail = timelineDetail.copy(startAt = timelineDetail.startAt.substringBefore(NEAREST_DATE_START_OUTPUT_FORMAT))))
            }.onFailure {
                setEvent(EnrollContract.EnrollEvent.FetchTimelineDetail(fetchEnrollState = LoadState.Error, timelineDetail = null))
            }
        }
    }

    private fun postCourse() {
        viewModelScope.launch {
            setEvent(EnrollContract.EnrollEvent.Enroll(loadState = LoadState.Loading))
            postCourseUseCase(enroll = currentState.enroll).onSuccess { result ->
                setEvent(EnrollContract.EnrollEvent.Enroll(loadState = LoadState.Success))
                AmplitudeUtils.updateIntUserProperty(propertyName = USER_POINT, propertyValue = result.userPoint)
                AmplitudeUtils.updateIntUserProperty(propertyName = USER_COURSE_COUNT, propertyValue = result.userCourseCount.toInt())
            }.onFailure {
                setEvent(EnrollContract.EnrollEvent.Enroll(loadState = LoadState.Error))
            }
        }
    }

    private fun postTimeline() {
        viewModelScope.launch {
            setEvent(EnrollContract.EnrollEvent.Enroll(loadState = LoadState.Loading))
            postTimelineUseCase(enroll = currentState.enroll).onSuccess { result ->
                setEvent(EnrollContract.EnrollEvent.Enroll(loadState = LoadState.Success))
                AmplitudeUtils.updateIntUserProperty(propertyName = DATE_SCHEDULE_NUM, propertyValue = result.dateScheduleNum.toInt())
            }.onFailure {
                setEvent(EnrollContract.EnrollEvent.Enroll(loadState = LoadState.Error))
            }
        }
    }

    private fun getPlaceSearchResult() {
        viewModelScope.launch {
            Pager(
                config = PagingConfig(pageSize = PAGE_SIZE),
                pagingSourceFactory = {
                    PlaceSearchPagingSource(
                        keyword = currentState.keyword,
                        getPlaceSearchResultUseCase = getPlaceSearchResultUseCase
                    )
                }
            ).flow
                .cachedIn(viewModelScope)
                .collectLatest { searchedPlaceInfos ->
                    setState { copy(searchedPlaceInfos = flowOf(searchedPlaceInfos)) }
                }
        }
    }
}
