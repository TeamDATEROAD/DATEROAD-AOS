package org.sopt.teamdateroad.presentation.ui.pointhistory.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.sopt.teamdateroad.presentation.type.EnrollType
import org.sopt.teamdateroad.presentation.ui.pointhistory.PointHistoryRoute

fun NavController.navigationPointHistory() {
    navigate(
        route = PointHistoryRoute.ROUTE
    )
}

fun NavGraphBuilder.pointHistoryGraph(
    padding: PaddingValues,
    popBackStack: () -> Unit,
    navigateToEnroll: (EnrollType, String, Int?) -> Unit
) {
    composable(route = PointHistoryRoute.ROUTE) {
        PointHistoryRoute(
            padding = padding,
            popBackStack = popBackStack,
            navigateToEnroll = navigateToEnroll
        )
    }
}

object PointHistoryRoute {
    const val ROUTE = "pointHistory"
}
