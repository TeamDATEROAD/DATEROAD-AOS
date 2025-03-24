package org.sopt.teamdateroad.presentation.ui.component.bottomsheet.model.collect

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import org.sopt.teamdateroad.R

enum class DateRoadCollectPointType(
    @StringRes val titleRes :Int,
    @StringRes val contentRes: Int,
    @DrawableRes val imageResource : Int,
){
    WATCH_ADS(
        titleRes = R.string.collect_point_ads_title,
        contentRes = R.string.collect_point_ads_content,
        imageResource = R.drawable.img_collect_ads
    ),
    COURSE_REGISTRATION(
        titleRes = R.string.collect_point_registration_title,
        contentRes = R.string.collect_point_registration_content,
        imageResource = R.drawable.img_collect_course
    )
}
