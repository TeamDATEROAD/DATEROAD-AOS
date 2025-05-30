package org.sopt.teamdateroad.presentation.ui.signin

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import org.sopt.teamdateroad.R
import org.sopt.teamdateroad.domain.model.SignIn
import org.sopt.teamdateroad.presentation.ui.component.button.DateRoadKakaoLoginButton
import org.sopt.teamdateroad.presentation.ui.component.view.DateRoadLoadingView
import org.sopt.teamdateroad.presentation.ui.component.view.DateRoadWebView
import org.sopt.teamdateroad.presentation.util.SignIn.PLATFORM
import org.sopt.teamdateroad.presentation.util.WebViewUrl.PRIVACY_POLICY_URL
import org.sopt.teamdateroad.presentation.util.modifier.noRippleClickable
import org.sopt.teamdateroad.presentation.util.view.LoadState
import org.sopt.teamdateroad.ui.theme.DateRoadTheme

fun setLayoutLoginKakaoClickListener(context: Context, callback: (OAuthToken?, Throwable?) -> Unit) {
    if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
        UserApiClient.instance.loginWithKakaoTalk(context, callback = callback)
    } else {
        UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
    }
}

@Composable
fun SignInRoute(
    viewModel: SignInViewModel = hiltViewModel(),
    navigateToOnboarding: () -> Unit,
    navigateToHome: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val callback: (OAuthToken?, Throwable?) -> Unit = { oAuthToken, _ ->
        if (oAuthToken != null) {
            viewModel.setKakaoAccessToken(oAuthToken.accessToken)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.checkAutoLogin()
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .collect { signInSideEffect ->
                when (signInSideEffect) {
                    is SignInContract.SignInSideEffect.NavigateToOnboarding -> navigateToOnboarding()
                    is SignInContract.SignInSideEffect.NavigateToHome -> navigateToHome()
                }
            }
    }

    LaunchedEffect(uiState.authTokenLoadState) {
        when (uiState.authTokenLoadState) {
            LoadState.Success -> viewModel.postSignIn(signIn = SignIn(PLATFORM))
            else -> Unit
        }
    }

    when (uiState.loadState) {
        LoadState.Idle -> {
            SignInScreen(
                signInUiState = uiState,
                onSignInClicked = {
                    setLayoutLoginKakaoClickListener(context = context, callback = callback)
                },
                onWebViewClicked = { viewModel.setEvent(SignInContract.SignInEvent.OnWebViewClick) },
                webViewClose = { viewModel.setEvent(SignInContract.SignInEvent.WebViewClose) }
            )
        }

        LoadState.Loading -> DateRoadLoadingView()

        LoadState.Success -> navigateToHome()

        LoadState.Error -> navigateToOnboarding()
    }
}

@Composable
fun SignInScreen(
    signInUiState: SignInContract.SignInUiState = SignInContract.SignInUiState(),
    onSignInClicked: () -> Unit,
    onWebViewClicked: () -> Unit,
    webViewClose: () -> Unit
) {
    if (signInUiState.isWebViewOpened) {
        DateRoadWebView(url = PRIVACY_POLICY_URL, onClose = webViewClose)
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DateRoadTheme.colors.purple600),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(270f))
                Image(painter = painterResource(id = R.drawable.img_splash_logo), contentDescription = null)
                Spacer(modifier = Modifier.weight(328f))
            }
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(591f))
                DateRoadKakaoLoginButton(
                    modifier = Modifier.padding(horizontal = 30.dp),
                    onClick = onSignInClicked
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "개인정보처리방침",
                    color = DateRoadTheme.colors.gray200,
                    style = DateRoadTheme.typography.bodyMed15,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.noRippleClickable(onClick = onWebViewClicked)
                )
                Spacer(modifier = Modifier.weight(124f))
            }
        }
    }
}
