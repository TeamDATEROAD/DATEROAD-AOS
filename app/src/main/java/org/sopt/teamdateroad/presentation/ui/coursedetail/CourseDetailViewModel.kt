package org.sopt.teamdateroad.presentation.ui.coursedetail

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import org.sopt.teamdateroad.domain.model.UsePoint
import org.sopt.teamdateroad.domain.usecase.DeleteCourseLikeUseCase
import org.sopt.teamdateroad.domain.usecase.DeleteCourseUseCase
import org.sopt.teamdateroad.domain.usecase.GetCourseDetailUseCase
import org.sopt.teamdateroad.domain.usecase.PostAdsPointUseCase
import org.sopt.teamdateroad.domain.usecase.PostCourseLikeUseCase
import org.sopt.teamdateroad.domain.usecase.PostUsePointUseCase
import org.sopt.teamdateroad.presentation.util.CourseDetailAmplitude.CLICK_COURSE_LIKES
import org.sopt.teamdateroad.presentation.util.CourseDetailAmplitude.COURSE_LIST_LIKE
import org.sopt.teamdateroad.presentation.util.Point
import org.sopt.teamdateroad.presentation.util.UserPropertyAmplitude.USER_FREE_REMAINED
import org.sopt.teamdateroad.presentation.util.UserPropertyAmplitude.USER_POINT
import org.sopt.teamdateroad.presentation.util.UserPropertyAmplitude.USER_PURCHASE_COUNT
import org.sopt.teamdateroad.presentation.util.amplitude.AmplitudeUtils
import org.sopt.teamdateroad.presentation.util.base.BaseViewModel
import org.sopt.teamdateroad.presentation.util.view.LoadState

@HiltViewModel
class CourseDetailViewModel @Inject constructor(
    private val deleteCourseUseCase: DeleteCourseUseCase,
    private val deleteCourseLikeUseCase: DeleteCourseLikeUseCase,
    private val getCourseDetailUseCase: GetCourseDetailUseCase,
    private val postCourseLikeUseCase: PostCourseLikeUseCase,
    private val postUsePointUseCase: PostUsePointUseCase,
    private val postAdsPointUseCase: PostAdsPointUseCase
) : BaseViewModel<CourseDetailContract.CourseDetailUiState, CourseDetailContract.CourseDetailSideEffect, CourseDetailContract.CourseDetailEvent>() {
    override fun createInitialState(): CourseDetailContract.CourseDetailUiState = CourseDetailContract.CourseDetailUiState()

    override suspend fun handleEvent(event: CourseDetailContract.CourseDetailEvent) {
        when (event) {
            is CourseDetailContract.CourseDetailEvent.OnDialogPointLack -> setState { copy(isPointCollectBottomSheetOpen = true) }
            is CourseDetailContract.CourseDetailEvent.DismissDialogPointLack -> setState { copy(isPointCollectBottomSheetOpen = false) }
            is CourseDetailContract.CourseDetailEvent.OnDialogLookedForFree -> setState { copy(isFreeReadDialogOpen = true) }
            is CourseDetailContract.CourseDetailEvent.DismissDialogLookedForFree -> setState { copy(isFreeReadDialogOpen = false) }
            is CourseDetailContract.CourseDetailEvent.OnDialogDeleteCourse -> setState { copy(isDeleteCourseDialogOpen = true) }
            is CourseDetailContract.CourseDetailEvent.DismissDialogDeleteCourse -> setState { copy(isDeleteCourseDialogOpen = false) }
            is CourseDetailContract.CourseDetailEvent.OnDialogReportCourse -> setState { copy(isReportCourseDialogOpen = true) }
            is CourseDetailContract.CourseDetailEvent.DismissDialogReportCourse -> setState { copy(isReportCourseDialogOpen = false) }
            is CourseDetailContract.CourseDetailEvent.OnDialogLookedByPoint -> setState { copy(isPointReadDialogOpen = true, hasPointReadDialogOpened = true) }
            is CourseDetailContract.CourseDetailEvent.DismissDialogLookedByPoint -> setState { copy(isPointReadDialogOpen = false) }
            is CourseDetailContract.CourseDetailEvent.OnLikeButtonClicked -> setState { copy(isLikedButtonChecked = !isLikedButtonChecked) }
            is CourseDetailContract.CourseDetailEvent.OnDeleteCourseBottomSheet -> setState { copy(isDeleteCourseBottomSheetOpen = true) }
            is CourseDetailContract.CourseDetailEvent.DismissDeleteCourseBottomSheet -> setState { copy(isDeleteCourseBottomSheetOpen = false) }
            is CourseDetailContract.CourseDetailEvent.OnReportCourseBottomSheet -> setState { copy(isReportCourseBottomSheetOpen = true) }
            is CourseDetailContract.CourseDetailEvent.DismissReportCourseBottomSheet -> setState { copy(isReportCourseBottomSheetOpen = false) }
            is CourseDetailContract.CourseDetailEvent.FetchCourseDetail -> setState { copy(loadState = event.loadState, courseDetail = event.courseDetail) }
            is CourseDetailContract.CourseDetailEvent.DeleteCourseLike -> setState { copy(courseDetail = event.courseDetail) }
            is CourseDetailContract.CourseDetailEvent.PostCourseLike -> setState { copy(courseDetail = event.courseDetail) }
            is CourseDetailContract.CourseDetailEvent.DeleteCourse -> setState { copy(deleteLoadState = event.deleteLoadState) }
            is CourseDetailContract.CourseDetailEvent.PostUsePoint -> setState { copy(usePointLoadState = event.usePointLoadState, courseDetail = courseDetail.copy(isAccess = event.isAccess)) }
            is CourseDetailContract.CourseDetailEvent.OnReportWebViewClicked -> setState { copy(isWebViewOpened = true) }
            is CourseDetailContract.CourseDetailEvent.DismissReportWebView -> setState { copy(isWebViewOpened = false) }
            CourseDetailContract.CourseDetailEvent.FailLoadAdsPoint -> setState { copy(loadState = LoadState.Loading) }
            CourseDetailContract.CourseDetailEvent.DismissFullAdsDialog -> setState { copy(isFullAdsDialogOpen = false) }
            CourseDetailContract.CourseDetailEvent.FullAds -> setState { copy(isFullAdsDialogOpen = true) }
        }
    }

    fun deleteCourseLike(courseId: Int) {
        viewModelScope.launch {
            setEvent(CourseDetailContract.CourseDetailEvent.DeleteCourseLike(courseDetail = currentState.courseDetail))
            deleteCourseLikeUseCase(courseId = courseId).onSuccess {
                setEvent(CourseDetailContract.CourseDetailEvent.DeleteCourseLike(courseDetail = currentState.courseDetail.copy(isUserLiked = false, like = currentState.courseDetail.like - 1)))
                AmplitudeUtils.trackEventWithProperty(eventName = CLICK_COURSE_LIKES, propertyName = COURSE_LIST_LIKE, propertyValue = currentState.courseDetail.isUserLiked)
            }.onFailure {
                setEvent(CourseDetailContract.CourseDetailEvent.DeleteCourseLike(courseDetail = currentState.courseDetail))
            }
        }
    }

    fun fetchCourseDetail(courseId: Int) {
        viewModelScope.launch {
            setEvent(CourseDetailContract.CourseDetailEvent.FetchCourseDetail(loadState = LoadState.Loading, courseDetail = currentState.courseDetail))
            getCourseDetailUseCase(courseId = courseId).onSuccess { courseDetail ->
                setEvent(CourseDetailContract.CourseDetailEvent.FetchCourseDetail(loadState = LoadState.Success, courseDetail = courseDetail))
            }.onFailure {
                setEvent(CourseDetailContract.CourseDetailEvent.FetchCourseDetail(loadState = LoadState.Error, courseDetail = currentState.courseDetail))
            }
        }
    }

    fun postCourseLike(courseId: Int) {
        viewModelScope.launch {
            setEvent(
                CourseDetailContract.CourseDetailEvent.PostCourseLike(courseDetail = currentState.courseDetail)
            )
            postCourseLikeUseCase(courseId = courseId).onSuccess {
                setEvent(CourseDetailContract.CourseDetailEvent.PostCourseLike(courseDetail = currentState.courseDetail.copy(isUserLiked = true, like = currentState.courseDetail.like + 1)))
                AmplitudeUtils.trackEventWithProperty(eventName = CLICK_COURSE_LIKES, propertyName = COURSE_LIST_LIKE, propertyValue = currentState.courseDetail.isUserLiked)
            }.onFailure {
                setEvent(CourseDetailContract.CourseDetailEvent.PostCourseLike(courseDetail = currentState.courseDetail))
            }
        }
    }

    fun postUsePoint(courseId: Int) {
        viewModelScope.launch {
            setEvent(CourseDetailContract.CourseDetailEvent.PostUsePoint(usePointLoadState = LoadState.Loading, isAccess = currentState.courseDetail.isAccess))
            postUsePointUseCase(courseId = courseId, usePoint = UsePoint(Point.POINT, Point.POINT_USED, Point.POINT_USED_DESCRIPTION)).onSuccess { result ->
                setEvent(CourseDetailContract.CourseDetailEvent.PostUsePoint(usePointLoadState = LoadState.Success, isAccess = true))
                AmplitudeUtils.updateIntUserProperty(propertyName = USER_POINT, propertyValue = result.userPoint)
                AmplitudeUtils.updateIntUserProperty(propertyName = USER_FREE_REMAINED, propertyValue = result.userFreeRemained)
                AmplitudeUtils.updateIntUserProperty(propertyName = USER_PURCHASE_COUNT, propertyValue = result.userPurchaseCount.toInt())
            }.onFailure {
                setEvent(CourseDetailContract.CourseDetailEvent.PostUsePoint(usePointLoadState = LoadState.Error, isAccess = currentState.courseDetail.isAccess))
            }
        }
    }

    fun deleteCourse(courseId: Int) {
        viewModelScope.launch {
            setEvent(
                CourseDetailContract.CourseDetailEvent.DeleteCourse(deleteLoadState = LoadState.Loading)
            )
            deleteCourseUseCase(courseId = courseId).onSuccess {
                setEvent(
                    CourseDetailContract.CourseDetailEvent.DeleteCourse(deleteLoadState = LoadState.Success)
                )
            }.onFailure {
                setEvent(
                    CourseDetailContract.CourseDetailEvent.DeleteCourse(deleteLoadState = LoadState.Error)
                )
            }
        }
    }

    fun postAdsPoint(courseId: Int) {
        setState { copy(loadState = LoadState.Loading) }
        viewModelScope.launch {
            postAdsPointUseCase().onSuccess {
                fetchCourseDetail(courseId)
            }.onFailure {
                setState { copy(loadState = LoadState.Loading) }
            }
        }
    }
}
