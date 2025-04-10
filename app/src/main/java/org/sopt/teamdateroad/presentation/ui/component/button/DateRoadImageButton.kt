package org.sopt.teamdateroad.presentation.ui.component.button

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.sopt.teamdateroad.R
import org.sopt.teamdateroad.ui.theme.DATEROADTheme
import org.sopt.teamdateroad.ui.theme.DateRoadTheme

@Composable
fun DateRoadImageButton(
    modifier: Modifier = Modifier,
    isEnabled: Boolean = false,
    enabledBackgroundColor: Color = DateRoadTheme.colors.purple600,
    enabledContentColor: Color = DateRoadTheme.colors.white,
    disabledBackgroundColor: Color = DateRoadTheme.colors.gray200,
    disabledContentColor: Color = DateRoadTheme.colors.gray400,
    iconResId: Int = R.drawable.ic_all_plus_white,
    cornerRadius: Dp,
    paddingHorizontal: Dp,
    paddingVertical: Dp,
    onClick: () -> Unit
) {
    DateRoadButton(
        modifier = modifier,
        backgroundColor = if (isEnabled) enabledBackgroundColor else disabledBackgroundColor,
        cornerRadius = cornerRadius,
        paddingHorizontal = paddingHorizontal,
        paddingVertical = paddingVertical,
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            tint = if (isEnabled) enabledContentColor else disabledContentColor
        )
    }
}

@Preview
@Composable
fun DateRoadImageButtonPreview() {
    DATEROADTheme {
        Column {
            DateRoadImageButton(
                isEnabled = true,
                onClick = {},
                cornerRadius = 14.dp,
                paddingHorizontal = 16.dp,
                paddingVertical = 8.dp
            )
            DateRoadImageButton(
                isEnabled = true,
                onClick = {},
                cornerRadius = 14.dp,
                paddingHorizontal = 12.dp,
                paddingVertical = 12.dp
            )
            DateRoadImageButton(
                isEnabled = false,
                onClick = {},
                cornerRadius = 14.dp,
                paddingHorizontal = 12.dp,
                paddingVertical = 12.dp
            )
            DateRoadImageButton(
                isEnabled = true,
                onClick = {},
                cornerRadius = 44.dp,
                paddingHorizontal = 12.dp,
                paddingVertical = 12.dp
            )
        }
    }
}
