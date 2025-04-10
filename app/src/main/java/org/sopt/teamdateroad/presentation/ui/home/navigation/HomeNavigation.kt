package org.sopt.teamdateroad.presentation.ui.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.sopt.teamdateroad.presentation.model.MainNavigationBarRoute
import org.sopt.teamdateroad.presentation.type.EnrollType
import org.sopt.teamdateroad.presentation.type.MainNavigationBarItemType
import org.sopt.teamdateroad.presentation.type.TimelineType
import org.sopt.teamdateroad.presentation.ui.home.HomeRoute

fun NavController.navigationHome(navOptions: NavOptions) {
    navigate(
        route = MainNavigationBarRoute.Home::class.simpleName.orEmpty(),
        navOptions = navOptions
    )
}

fun NavGraphBuilder.homeNavGraph(
    padding: PaddingValues,
    navigateToPointHistory: () -> Unit,
    navigateToLook: (MainNavigationBarItemType) -> Unit,
    navigateToTimelineDetail: (TimelineType, Int) -> Unit,
    navigateToEnroll: (EnrollType, String, Int?) -> Unit,
    navigateToAdvertisement: (Int) -> Unit,
    navigateToCourseDetail: (Int) -> Unit
) {
    composable(route = MainNavigationBarRoute.Home::class.simpleName.orEmpty()) {
        HomeRoute(
            padding = padding,
            navigateToPointHistory = navigateToPointHistory,
            navigateToLook = navigateToLook,
            navigateToTimelineDetail = navigateToTimelineDetail,
            navigateToEnroll = navigateToEnroll,
            navigateToAdvertisementDetail = navigateToAdvertisement,
            navigateToCourseDetail = navigateToCourseDetail
        )
    }
}
