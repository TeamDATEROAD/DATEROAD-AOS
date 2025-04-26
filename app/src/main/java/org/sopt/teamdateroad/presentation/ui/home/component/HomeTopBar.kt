package org.sopt.teamdateroad.presentation.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.teamdateroad.R
import org.sopt.teamdateroad.presentation.ui.component.tag.DateRoadPointTag
import org.sopt.teamdateroad.ui.theme.DateRoadTheme

@Composable
fun DateRoadHomeTopBar(
    title: String = "0 P",
    profileImage: String? = null,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .background(Color.Transparent)
            .padding(start = 11.dp,end = 16.dp),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_dateroad_logo),
            contentDescription = null,
            tint = DateRoadTheme.colors.white,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(54.dp)
        )
        DateRoadPointTag(
            text = title,
            profileImage = profileImage,
            onClick = onClick,
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}

@Preview
@Composable
fun DateRoadHomeTopBarPreview() {
    Column {
        DateRoadHomeTopBar("5000 P")
    }
}
