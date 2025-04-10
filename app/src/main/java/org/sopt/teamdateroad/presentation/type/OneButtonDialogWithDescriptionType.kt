package org.sopt.teamdateroad.presentation.type

import androidx.annotation.StringRes
import org.sopt.teamdateroad.R

enum class OneButtonDialogWithDescriptionType(
    @StringRes val titleRes: Int,
    @StringRes val descriptionRes: Int,
    @StringRes val buttonTextRes: Int
) {
    ENROLL_COURSE(
        titleRes = R.string.one_button_dialog_with_description_enroll_course_title,
        descriptionRes = R.string.one_button_dialog_with_description_enroll_course_description,
        buttonTextRes = R.string.dialog_checked
    ),
    CANNOT_ENROLL_COURSE(
        titleRes = R.string.one_button_dialog_with_description_cannot_enroll_course_title,
        descriptionRes = R.string.one_button_dialog_with_description_cannot_enroll_course_description,
        buttonTextRes = R.string.dialog_checked
    ),
    FULL_ADS(
        titleRes = R.string.one_button_dialog_with_description_full_ads_title,
        descriptionRes = R.string.one_button_dialog_with_description_full_ads_description,
        buttonTextRes = R.string.dialog_checked
    )
}
