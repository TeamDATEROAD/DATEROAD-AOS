package org.sopt.teamdateroad.presentation.ui.pointhistory.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.teamdateroad.R
import org.sopt.teamdateroad.domain.model.UserPoint
import org.sopt.teamdateroad.presentation.ui.component.button.DateRoadBasicButton
import org.sopt.teamdateroad.ui.theme.DateRoadTheme

@Composable
fun PointHistoryPointBox(
    modifier: Modifier = Modifier,
    userPoint: UserPoint,
    onClickCollectPoint: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.point_box_nickname, userPoint.name),
            color = DateRoadTheme.colors.gray400,
            style = DateRoadTheme.typography.bodyBold13
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = userPoint.point,
            color = DateRoadTheme.colors.black,
            style = DateRoadTheme.typography.titleExtra24,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(20.dp))
        DateRoadBasicButton(
            textContent = stringResource(R.string.point_box_get_point_button_text),
            onClick = onClickCollectPoint
        )
    }
}

@Preview
@Composable
fun PointHistoryPointBoxPreview() {
    Column {
        PointHistoryPointBox(
            userPoint = UserPoint(),
            onClickCollectPoint = {}
        )
    }
}
