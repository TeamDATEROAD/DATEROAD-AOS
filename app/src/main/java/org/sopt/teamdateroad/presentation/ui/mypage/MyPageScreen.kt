package org.sopt.teamdateroad.presentation.ui.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import org.sopt.teamdateroad.R
import org.sopt.teamdateroad.data.dataremote.util.Point
import org.sopt.teamdateroad.domain.model.Profile
import org.sopt.teamdateroad.presentation.type.DateTagType.Companion.getDateTagTypeByName
import org.sopt.teamdateroad.presentation.type.MyCourseType
import org.sopt.teamdateroad.presentation.type.MyPageMenuType
import org.sopt.teamdateroad.presentation.type.ProfileType
import org.sopt.teamdateroad.presentation.type.TagType
import org.sopt.teamdateroad.presentation.type.TwoButtonDialogType
import org.sopt.teamdateroad.presentation.type.TwoButtonDialogWithDescriptionType
import org.sopt.teamdateroad.presentation.ui.component.button.DateRoadTextButton
import org.sopt.teamdateroad.presentation.ui.component.dialog.DateRoadTwoButtonDialog
import org.sopt.teamdateroad.presentation.ui.component.dialog.DateRoadTwoButtonDialogWithDescription
import org.sopt.teamdateroad.presentation.ui.component.tag.DateRoadImageTag
import org.sopt.teamdateroad.presentation.ui.component.topbar.DateRoadLeftTitleTopBar
import org.sopt.teamdateroad.presentation.ui.component.view.DateRoadErrorView
import org.sopt.teamdateroad.presentation.ui.component.view.DateRoadIdleView
import org.sopt.teamdateroad.presentation.ui.component.view.DateRoadLoadingView
import org.sopt.teamdateroad.presentation.ui.component.view.DateRoadWebView
import org.sopt.teamdateroad.presentation.ui.mypage.component.MyPageButton
import org.sopt.teamdateroad.presentation.ui.mypage.component.MyPagePointBox
import org.sopt.teamdateroad.presentation.util.WebViewUrl.ASK_URL
import org.sopt.teamdateroad.presentation.util.modifier.noRippleClickable
import org.sopt.teamdateroad.presentation.util.view.LoadState
import org.sopt.teamdateroad.ui.theme.DATEROADTheme
import org.sopt.teamdateroad.ui.theme.DateRoadTheme

@Composable
fun MyPageRoute(
    padding: PaddingValues,
    viewModel: MyPageViewModel = hiltViewModel(),
    navigateToPointHistory: () -> Unit,
    navigateToMyCourse: (MyCourseType) -> Unit,
    navigateToPointGuide: () -> Unit,
    navigateToSignIn: () -> Unit,
    navigateToProfile: (ProfileType) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        viewModel.fetchProfile()
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .collect { myPageSideEffect ->
                when (myPageSideEffect) {
                    is MyPageContract.MyPageSideEffect.NavigateToEditProfile -> navigateToProfile(ProfileType.EDIT)
                    is MyPageContract.MyPageSideEffect.NavigateToPointHistory -> navigateToPointHistory()
                    is MyPageContract.MyPageSideEffect.NavigateToMyCourse -> navigateToMyCourse(MyCourseType.ENROLL)
                    is MyPageContract.MyPageSideEffect.NavigateToPointGuide -> navigateToPointGuide()
                    is MyPageContract.MyPageSideEffect.NavigateToLogin -> navigateToSignIn()
                }
            }
    }

    when (uiState.deleteUserLoadState) {
        LoadState.Success -> navigateToSignIn()
        else -> Unit
    }

    when (uiState.deleteSignOutLoadState) {
        LoadState.Success -> {
            navigateToSignIn()
        }

        else -> Unit
    }

    when (uiState.loadState) {
        LoadState.Idle -> DateRoadIdleView()

        LoadState.Loading -> DateRoadLoadingView()

        LoadState.Success -> {
            MyPageScreen(
                padding = padding,
                myPageUiState = uiState,
                deleteLogout = {
                    viewModel.deleteLogout()
                },
                deleteWithdrawal = {
                    viewModel.withdrawal()
                },
                navigateToEditProfile = {
                    viewModel.setSideEffect(MyPageContract.MyPageSideEffect.NavigateToEditProfile)
                },
                navigateToPointHistory = { viewModel.setSideEffect(MyPageContract.MyPageSideEffect.NavigateToPointHistory) },
                navigateToMyCourse = { viewModel.setSideEffect(MyPageContract.MyPageSideEffect.NavigateToMyCourse) },
                navigateToPointGuide = { viewModel.setSideEffect(MyPageContract.MyPageSideEffect.NavigateToPointGuide) },
                setLogoutDialog = { showLogoutDialog ->
                    viewModel.setEvent(
                        MyPageContract.MyPageEvent.SetLogoutDialog(showLogoutDialog = showLogoutDialog)
                    )
                },
                setWithdrawalDialog = { showWithdrawalDialog ->
                    viewModel.setEvent(
                        MyPageContract.MyPageEvent.SetWithdrawalDialog(showWithdrawalDialog = showWithdrawalDialog)
                    )
                },
                showWebView = {
                    viewModel.setEvent(MyPageContract.MyPageEvent.OnWebViewClick)
                },
                webViewClose = { viewModel.setEvent(MyPageContract.MyPageEvent.WebViewClose) }
            )
        }

        LoadState.Error -> DateRoadErrorView()
    }
}

@Composable
fun MyPageScreen(
    padding: PaddingValues,
    myPageUiState: MyPageContract.MyPageUiState = MyPageContract.MyPageUiState(),
    deleteLogout: () -> Unit,
    deleteWithdrawal: () -> Unit,
    navigateToEditProfile: () -> Unit,
    navigateToPointHistory: () -> Unit,
    navigateToMyCourse: () -> Unit,
    navigateToPointGuide: () -> Unit,
    setLogoutDialog: (Boolean) -> Unit,
    setWithdrawalDialog: (Boolean) -> Unit,
    showWebView: (Boolean) -> Unit,
    webViewClose: () -> Unit
) {
    if (myPageUiState.showWebView) {
        DateRoadWebView(url = ASK_URL, onClose = webViewClose)
    } else {
        Column(
            modifier = Modifier
                .padding(paddingValues = padding)
                .background(color = DateRoadTheme.colors.white)
                .fillMaxSize(),
            horizontalAlignment = Alignment.End
        ) {
            Column(
                modifier = Modifier
                    .background(color = DateRoadTheme.colors.gray100, shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
            ) {
                DateRoadLeftTitleTopBar(title = stringResource(id = R.string.top_bar_title_my_page))
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    when (myPageUiState.profile.imageUrl) {
                        null -> {
                            Image(
                                modifier = Modifier
                                    .width(44.dp)
                                    .aspectRatio(1f)
                                    .clip(CircleShape),
                                painter = painterResource(id = R.drawable.img_profile_default),
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                        }

                        else -> {
                            AsyncImage(
                                modifier = Modifier
                                    .width(44.dp)
                                    .aspectRatio(1f)
                                    .clip(CircleShape),
                                model = ImageRequest.Builder(context = LocalContext.current)
                                    .data(myPageUiState.profile.imageUrl)
                                    .crossfade(true)
                                    .build(),
                                placeholder = null,
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(13.dp))
                    Text(
                        text = myPageUiState.profile.name,
                        color = DateRoadTheme.colors.black,
                        style = DateRoadTheme.typography.titleExtra24
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Icon(
                        modifier = Modifier.noRippleClickable(onClick = {
                            navigateToEditProfile()
                        }),
                        painter = painterResource(id = R.drawable.ic_my_page_pencil),
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                LazyRow(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(7.dp)
                ) {
                    items(myPageUiState.profile.tag) { tag ->
                        tag.getDateTagTypeByName()?.let { tagType ->
                            DateRoadImageTag(
                                textContent = stringResource(id = tagType.titleRes),
                                imageContent = tagType.imageRes,
                                tagContentType = TagType.MY_PAGE_DATE
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                MyPagePointBox(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    nickname = myPageUiState.profile.name,
                    point = myPageUiState.profile.point,
                    onClick = navigateToPointHistory
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            MyPageMenuType.entries.forEach { myPageMenuType ->
                MyPageButton(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    myPageMenuType = myPageMenuType,
                    onClick = {
                        when (myPageMenuType) {
                            MyPageMenuType.MY_COURSE_ENROLL -> navigateToMyCourse()
                            MyPageMenuType.POINT_SYSTEM -> navigateToPointGuide()
                            MyPageMenuType.QUESTION -> showWebView(true)
                            MyPageMenuType.LOGOUT -> setLogoutDialog(true)
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            DateRoadTextButton(
                modifier = Modifier.align(Alignment.Start),
                textContent = stringResource(id = R.string.my_page_menu_withdrawal),
                textStyle = DateRoadTheme.typography.bodyMed13,
                textColor = DateRoadTheme.colors.gray400,
                paddingHorizontal = 20.dp,
                paddingVertical = 6.dp,
                onClick = { setWithdrawalDialog(true) }
            )
            Spacer(modifier = Modifier.height(30.dp))
        }

        if (myPageUiState.showLogoutDialog) {
            DateRoadTwoButtonDialog(
                twoButtonDialogType = TwoButtonDialogType.LOGOUT,
                onDismissRequest = { setLogoutDialog(false) },
                onClickConfirm = deleteLogout,
                onClickDismiss = { setLogoutDialog(false) }
            )
        }

        if (myPageUiState.showWithdrawalDialog) {
            DateRoadTwoButtonDialogWithDescription(
                twoButtonDialogWithDescriptionType = TwoButtonDialogWithDescriptionType.WITHDRAWAL,
                onDismissRequest = { setWithdrawalDialog(false) },
                onClickConfirm = { setWithdrawalDialog(false) },
                onClickDismiss = { deleteWithdrawal() }
            )
        }
    }
}

@Preview
@Composable
fun MyPageScreenPreview() {
    DATEROADTheme {
        MyPageScreen(
            padding = PaddingValues(0.dp),
            myPageUiState = MyPageContract.MyPageUiState(
                profile = Profile("지현", listOf("드라이브", "쇼핑", "실내"), "200 $Point")
            ),
            deleteLogout = {},
            deleteWithdrawal = {},
            navigateToEditProfile = {},
            navigateToPointHistory = {},
            navigateToMyCourse = {},
            navigateToPointGuide = {},
            setLogoutDialog = {},
            setWithdrawalDialog = {},
            showWebView = {},
            webViewClose = {}
        )
    }
}
