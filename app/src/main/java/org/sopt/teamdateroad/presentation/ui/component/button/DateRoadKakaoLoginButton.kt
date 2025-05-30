package org.sopt.teamdateroad.presentation.ui.component.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import org.sopt.teamdateroad.R
import org.sopt.teamdateroad.ui.theme.DATEROADTheme
import org.sopt.teamdateroad.ui.theme.DateRoadTheme

@Composable
fun DateRoadKakaoLoginButton(
    modifier: Modifier = Modifier,
    backgroundColor: Color = DateRoadTheme.colors.kakaoYellow,
    contentColor: Color = DateRoadTheme.colors.black,
    onClick: () -> Unit = {}
) {
    DateRoadButton(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = backgroundColor,
        cornerRadius = 6.dp,
        paddingVertical = 11.dp,
        paddingHorizontal = 14.dp,
        onClick = onClick
    ) {
        Box(
            modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_kakao_logo),
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .align(Alignment.CenterStart)
            )
            Text(
                text = stringResource(id = R.string.kakao_login),
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 1.5.em,
                color = contentColor,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
fun DateRoadKakaoLoginButtonPreview() {
    DATEROADTheme {
        DateRoadKakaoLoginButton()
    }
}
