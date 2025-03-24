package org.sopt.teamdateroad.presentation.ui.component.bottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.teamdateroad.R
import org.sopt.teamdateroad.presentation.ui.component.bottomsheet.model.collect.DateRoadCollectPointType
import org.sopt.teamdateroad.ui.theme.DateRoadTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRoadPointBottomSheet(
    isBottomSheetOpen: Boolean,
    onClick : (DateRoadCollectPointType) -> Unit
) {
    DefaultDateRoadBottomSheet(
        isBottomSheetOpen = isBottomSheetOpen,
        content = {
            Column {
                DateRoadBottonSheetContent(
                    dateLoadCollectPoint = DateRoadCollectPointType.WATCH_ADS,
                    onClick = onClick
                )
                DateRoadBottonSheetContent(
                    dateLoadCollectPoint = DateRoadCollectPointType.COURSE_REGISTRATION,
                    onClick = onClick
                )
            }
        }
    )
}

@Composable
fun DateRoadBottonSheetContent(
    dateLoadCollectPoint: DateRoadCollectPointType,
    onClick: (DateRoadCollectPointType) -> Unit,
){
    Row(
       modifier = Modifier
           .fillMaxWidth()
           .clickable {
               onClick(dateLoadCollectPoint)
           }
           .padding(
               start = 20.dp,
               end = 23.dp,
           )
           .padding(vertical = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            modifier = Modifier.size(40.dp),
            painter = painterResource(id = dateLoadCollectPoint.imageResource),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(14.dp))
        Column(
            modifier = Modifier.wrapContentWidth()
        ) {
            Text(
                text = stringResource(dateLoadCollectPoint.titleRes),
                color = DateRoadTheme.colors.gray600,
                style = DateRoadTheme.typography.bodyBold15
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(dateLoadCollectPoint.contentRes),
                color = DateRoadTheme.colors.gray400,
                style = DateRoadTheme.typography.bodySemi13

            )
        }
        Image(
            painter = painterResource(R.drawable.ic_my_page_arrow),
            contentDescription = null,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Preview
@Composable
fun DateRoadPointBottomSheetPreView(){
    DateRoadPointBottomSheet(
        isBottomSheetOpen = true ,
        onClick = {}
    )
}
