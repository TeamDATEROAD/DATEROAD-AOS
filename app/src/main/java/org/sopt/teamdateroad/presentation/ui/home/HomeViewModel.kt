package org.sopt.teamdateroad.presentation.ui.home

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import org.sopt.teamdateroad.domain.model.NearestTimeline
import org.sopt.teamdateroad.domain.type.SortByType
import org.sopt.teamdateroad.domain.usecase.GetAdvertisementsUseCase
import org.sopt.teamdateroad.domain.usecase.GetNearestTimelineUseCase
import org.sopt.teamdateroad.domain.usecase.GetSortedCoursesUseCase
import org.sopt.teamdateroad.domain.usecase.GetUserPointUseCase
import org.sopt.teamdateroad.domain.usecase.SetNicknameUseCase
import org.sopt.teamdateroad.presentation.util.base.BaseViewModel
import org.sopt.teamdateroad.presentation.util.view.LoadState

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAdvertisementsUseCase: GetAdvertisementsUseCase,
    private val getNearestTimelineUseCase: GetNearestTimelineUseCase,
    private val getSortedCoursesUseCase: GetSortedCoursesUseCase,
    private val getUserPointUseCase: GetUserPointUseCase,
    private val setNicknameUseCase: SetNicknameUseCase
) : BaseViewModel<HomeContract.HomeUiState, HomeContract.HomeSideEffect, HomeContract.HomeEvent>() {
    override fun createInitialState(): HomeContract.HomeUiState = HomeContract.HomeUiState()

    override suspend fun handleEvent(event: HomeContract.HomeEvent) {
        when (event) {
            is HomeContract.HomeEvent.ChangeBannerPage -> setState { copy(currentBannerPage = event.page) }
            is HomeContract.HomeEvent.FetchAdvertisements -> setState { copy(loadState = event.loadState, advertisements = event.advertisements) }
            is HomeContract.HomeEvent.FetchLatestCourses -> setState { copy(loadState = event.loadState, latestCourses = event.latestCourses) }
            is HomeContract.HomeEvent.FetchTopLikedCourses -> setState { copy(loadState = event.loadState, topLikedCourses = event.topLikedCourses) }
            is HomeContract.HomeEvent.FetchNearestTimeline -> setState { copy(loadState = event.loadState, nearestTimeline = event.nearestTimeline) }
            is HomeContract.HomeEvent.FetchUserPoint -> setState { copy(loadState = event.loadState, userPoint = event.userPoint) }
            is HomeContract.HomeEvent.FetchProfileImage -> setState { copy(loadState = loadState, profileImageUrl = event.profileImageUrl) }
        }
    }

    fun fetchAdvertisements() {
        viewModelScope.launch {
            setEvent(HomeContract.HomeEvent.FetchAdvertisements(loadState = LoadState.Loading, advertisements = currentState.advertisements))
            getAdvertisementsUseCase()
                .onSuccess { advertisements ->
                    setEvent(HomeContract.HomeEvent.FetchAdvertisements(loadState = LoadState.Success, advertisements = advertisements))
                }
                .onFailure {
                    setEvent(HomeContract.HomeEvent.FetchAdvertisements(loadState = LoadState.Error, advertisements = currentState.advertisements))
                }
        }
    }

    fun fetchNearestDate() {
        viewModelScope.launch {
            setEvent(HomeContract.HomeEvent.FetchNearestTimeline(loadState = LoadState.Loading, nearestTimeline = NearestTimeline()))
            getNearestTimelineUseCase()
                .onSuccess { nearestTimeline ->
                    setEvent(HomeContract.HomeEvent.FetchNearestTimeline(loadState = LoadState.Success, nearestTimeline = nearestTimeline))
                }
                .onFailure {
                    setEvent(HomeContract.HomeEvent.FetchNearestTimeline(loadState = LoadState.Success, nearestTimeline = NearestTimeline()))
                }
        }
    }

    fun fetchSortedCourses(sortBy: SortByType) {
        viewModelScope.launch {
            setEvent(HomeContract.HomeEvent.FetchLatestCourses(loadState = LoadState.Loading, latestCourses = currentState.latestCourses))
            getSortedCoursesUseCase(sortBy)
                .onSuccess { responseCoursesDto ->
                    if (sortBy == SortByType.POPULAR) {
                        setEvent(HomeContract.HomeEvent.FetchTopLikedCourses(loadState = LoadState.Success, topLikedCourses = responseCoursesDto))
                    } else {
                        setEvent(HomeContract.HomeEvent.FetchLatestCourses(loadState = LoadState.Success, latestCourses = responseCoursesDto))
                    }
                }
                .onFailure {
                    setEvent(HomeContract.HomeEvent.FetchLatestCourses(loadState = LoadState.Error, latestCourses = currentState.latestCourses))
                }
        }
    }

    fun fetchUserPoint() {
        viewModelScope.launch {
            setEvent(HomeContract.HomeEvent.FetchUserPoint(loadState = LoadState.Loading, userPoint = currentState.userPoint))
            getUserPointUseCase()
                .onSuccess { userPoint ->
                    setEvent(HomeContract.HomeEvent.FetchUserPoint(loadState = LoadState.Success, userPoint = userPoint))
                    setNicknameUseCase(nickname = userPoint.name)
                }
                .onFailure {
                    setEvent(HomeContract.HomeEvent.FetchUserPoint(loadState = LoadState.Error, userPoint = currentState.userPoint))
                }
        }
    }
}
