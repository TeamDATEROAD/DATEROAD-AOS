package org.sopt.teamdateroad.presentation.ui.coursedetail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.sopt.teamdateroad.ui.theme.DateRoadTheme

@Composable
fun CourseDetailBasicInfo(
    date: String,
    title: String,
    totalTime: String,
    totalCostTag: String,
    city: String
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(18.dp))
        Text(
            text = date,
            style = DateRoadTheme.typography.bodySemi15,
            color = DateRoadTheme.colors.gray400
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = title,
            style = DateRoadTheme.typography.titleExtra24,
            color = DateRoadTheme.colors.black
        )
        Spacer(modifier = Modifier.height(16.dp))
        CourseDetailInfoBar(
            totalTime = totalTime,
            totalCostTag = totalCostTag,
            city = city
        )
    }
}
