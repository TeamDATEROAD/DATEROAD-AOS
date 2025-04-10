package org.sopt.teamdateroad.presentation.ui.component.view

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.teamdateroad.R
import org.sopt.teamdateroad.presentation.ui.component.topbar.DateRoadBasicTopBar
import org.sopt.teamdateroad.ui.theme.DateRoadTheme

@Composable
fun DateRoadErrorView() {
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = DateRoadTheme.colors.white),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DateRoadBasicTopBar(
            title = "",
            onLeftIconClick = { backDispatcher?.onBackPressed() },
            leftIconResource = R.drawable.ic_top_bar_back_white
        )
        Spacer(modifier = Modifier.weight(131f))
        Image(
            modifier = Modifier
                .fillMaxWidth(),
            painter = painterResource(id = R.drawable.img_error_server),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(id = R.string.error_view_server_title),
            color = DateRoadTheme.colors.gray500,
            style = DateRoadTheme.typography.titleExtra20,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(id = R.string.error_view_server_description),
            color = DateRoadTheme.colors.gray500,
            style = DateRoadTheme.typography.bodyMed15,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.weight(186f))
    }
}

@Preview
@Composable
fun ErrorPreview() {
    DateRoadErrorView()
}
