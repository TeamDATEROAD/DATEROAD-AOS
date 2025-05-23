package org.sopt.teamdateroad.presentation.ui.mycourse

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import org.sopt.teamdateroad.R
import org.sopt.teamdateroad.domain.model.Course
import org.sopt.teamdateroad.presentation.type.EmptyViewType
import org.sopt.teamdateroad.presentation.type.EnrollType
import org.sopt.teamdateroad.presentation.type.MyCourseType
import org.sopt.teamdateroad.presentation.ui.component.card.DateRoadCourseCard
import org.sopt.teamdateroad.presentation.ui.component.topbar.DateRoadBasicTopBar
import org.sopt.teamdateroad.presentation.ui.component.view.DateRoadEmptyView
import org.sopt.teamdateroad.presentation.ui.component.view.DateRoadErrorView
import org.sopt.teamdateroad.presentation.ui.component.view.DateRoadIdleView
import org.sopt.teamdateroad.presentation.ui.component.view.DateRoadLoadingView
import org.sopt.teamdateroad.presentation.util.MyCourseAmplitude.CLICK_PURCHASED_BACK
import org.sopt.teamdateroad.presentation.util.MyCourseAmplitude.VIEW_PURCHASED_COURSE
import org.sopt.teamdateroad.presentation.util.ViewPath.MY_COURSE_READ
import org.sopt.teamdateroad.presentation.util.amplitude.AmplitudeUtils
import org.sopt.teamdateroad.presentation.util.view.LoadState
import org.sopt.teamdateroad.ui.theme.DATEROADTheme
import org.sopt.teamdateroad.ui.theme.DateRoadTheme

@Composable
fun MyCourseRoute(
    padding: PaddingValues,
    viewModel: MyCourseViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
    navigateToEnroll: (EnrollType, String, Int?) -> Unit,
    navigateToCourseDetail: (Int) -> Unit,
    myCourseType: MyCourseType
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        viewModel.setEvent(
            MyCourseContract.MyCourseEvent.SetMyCourseType(myCourseType = myCourseType)
        )

        when (myCourseType) {
            MyCourseType.ENROLL -> viewModel.fetchMyCourseEnroll()
            MyCourseType.READ -> {
                viewModel.fetchMyCourseRead()
                AmplitudeUtils.trackEvent(eventName = VIEW_PURCHASED_COURSE)
            }
        }
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .collect { myCourseSideEffect ->
                when (myCourseSideEffect) {
                    is MyCourseContract.MyCourseSideEffect.NavigateToEnroll -> navigateToEnroll(EnrollType.TIMELINE, MY_COURSE_READ, myCourseSideEffect.courseId)
                    is MyCourseContract.MyCourseSideEffect.NavigateToCourseDetail -> navigateToCourseDetail(myCourseSideEffect.courseId)
                    is MyCourseContract.MyCourseSideEffect.PopBackStack -> popBackStack()
                }
            }
    }

    when (uiState.loadState) {
        LoadState.Idle -> DateRoadIdleView()

        LoadState.Loading -> DateRoadLoadingView()

        LoadState.Success -> {
            MyCourseScreen(
                padding = padding,
                myCourseUiState = uiState,
                onIconClick = {
                    popBackStack()
                    AmplitudeUtils.trackEvent(CLICK_PURCHASED_BACK)
                },
                navigateToEnroll = { courseId -> viewModel.setSideEffect(MyCourseContract.MyCourseSideEffect.NavigateToEnroll(courseId = courseId)) },
                navigateToCourseDetail = { courseId -> viewModel.setSideEffect(MyCourseContract.MyCourseSideEffect.NavigateToCourseDetail(courseId = courseId)) }
            )
        }

        LoadState.Error -> DateRoadErrorView()
    }
}

@Composable
fun MyCourseScreen(
    padding: PaddingValues,
    myCourseUiState: MyCourseContract.MyCourseUiState = MyCourseContract.MyCourseUiState(),
    onIconClick: () -> Unit,
    navigateToEnroll: (Int) -> Unit,
    navigateToCourseDetail: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .background(color = DateRoadTheme.colors.white)
    ) {
        DateRoadBasicTopBar(
            title = stringResource(id = myCourseUiState.myCourseType.topBarTitleRes),
            leftIconResource = R.drawable.ic_top_bar_back_white,
            backGroundColor = DateRoadTheme.colors.white,
            onLeftIconClick = onIconClick
        )
        if (myCourseUiState.courses.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Spacer(modifier = Modifier.weight(60f))
                DateRoadEmptyView(
                    emptyViewType = when (myCourseUiState.myCourseType) {
                        MyCourseType.ENROLL -> EmptyViewType.MY_COURSE_ENROLL
                        MyCourseType.READ -> EmptyViewType.MY_COURSE_READ
                    }
                )
                Spacer(modifier = Modifier.weight(165f))
            }
        } else {
            LazyColumn {
                items(myCourseUiState.courses) { course ->
                    DateRoadCourseCard(
                        course = course,
                        onClick = {
                            when (myCourseUiState.myCourseType) {
                                MyCourseType.ENROLL -> navigateToCourseDetail(course.courseId)
                                MyCourseType.READ -> navigateToEnroll(course.courseId)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun MyCourseScreenPreview() {
    DATEROADTheme {
        MyCourseScreen(
            padding = PaddingValues(0.dp),
            myCourseUiState = MyCourseContract.MyCourseUiState(
                loadState = LoadState.Success,
                myCourseType = MyCourseType.ENROLL,
                courses = listOf(
                    Course(
                        courseId = 1,
                        thumbnail = "https://avatars.githubusercontent.com/u/103172971?v=4",
                        city = "건대/성수/왕십리",
                        title = "여기 야키니쿠 꼭 먹으러 가세요\n하지만 일본에 있습니다.",
                        cost = "10만원 초과",
                        duration = "10시간",
                        like = "99999"
                    ),
                    Course(
                        courseId = 2,
                        thumbnail = "https://avatars.githubusercontent.com/u/103172971?v=4",
                        city = "부천",
                        title = "여기 야키니쿠 꼭 먹으러 가세요.",
                        cost = "10만원 초과",
                        duration = "10시간",
                        like = "999"
                    ),
                    Course(
                        courseId = 3,
                        thumbnail = "https://avatars.githubusercontent.com/u/103172971?v=4",
                        city = "건대/성수/왕십리",
                        title = "여기 야키니쿠 꼭 먹으러 가세요\n하지만 일본에 있습니다.하지만 일본에 있습니다.하지만 일본에 있습니다.하지만 일본에 있습니다.하지만 일본에 있습니다.",
                        cost = "10만원 초과",
                        duration = "10시간",
                        like = "999"
                    ),
                    Course(
                        courseId = 4,
                        thumbnail = "https://avatars.githubusercontent.com/u/103172971?v=4",
                        city = "건대/성수/왕십리",
                        title = "여기 야키니쿠 꼭 먹으러 가세요\n하지만 일본에 있습니다.",
                        cost = "10만원 초과",
                        duration = "10시간",
                        like = "999"
                    )
                )
            ),
            onIconClick = {},
            navigateToEnroll = {},
            navigateToCourseDetail = {}
        )
    }
}
