package org.sopt.teamdateroad.presentation.ui.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.sopt.teamdateroad.presentation.type.ProfileType
import org.sopt.teamdateroad.presentation.ui.onboarding.OnboardingRoute

fun NavController.navigationOnboarding() {
    navigate(
        route = OnboardingRoute.ROUTE
    )
}

fun NavGraphBuilder.onboardingNavGraph(
    navigateToProfile: (ProfileType) -> Unit,
    navigateToSignIn: () -> Unit
) {
    composable(route = OnboardingRoute.ROUTE) {
        OnboardingRoute(
            navigateToProfile = navigateToProfile,
            navigateToSignIn = navigateToSignIn
        )
    }
}

object OnboardingRoute {
    const val ROUTE = "onboarding"
}
