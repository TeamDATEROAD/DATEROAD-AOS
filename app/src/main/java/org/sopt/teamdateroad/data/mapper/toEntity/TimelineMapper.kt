package org.sopt.teamdateroad.data.mapper.toEntity

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import org.sopt.teamdateroad.data.dataremote.model.response.ResponseNearestTimelineDto
import org.sopt.teamdateroad.data.dataremote.util.Date
import org.sopt.teamdateroad.domain.type.MonthType

fun String.toCourseDetailDate(): String = SimpleDateFormat(Date.INPUT_FORMAT, Locale.getDefault()).parse(this)?.let { SimpleDateFormat(Date.COURSE_DETAIL_OUTPUT_FORMAT, Locale.getDefault()).format(it) }.orEmpty()

fun String.toBasicDates(): String = SimpleDateFormat(Date.INPUT_FORMAT, Locale.getDefault()).parse(this)?.let { SimpleDateFormat(Date.DATE_OUTPUT_FORMAT, Locale.getDefault()).format(it) }.orEmpty()

fun String.fromDateToEnrollDate(): String = LocalDate.parse(this, DateTimeFormatter.ofPattern(Date.DATE_OUTPUT_FORMAT)).format(DateTimeFormatter.ofPattern(Date.INPUT_FORMAT))

fun Int.toDDayString(): String = when {
    this > 0 -> Date.D_DAY_OUTPUT_FORMAT + this
    this == 0 -> Date.D_DAY_DEFAULT_LABEL
    else -> ""
}

fun String.toStartAtString(): String = this + Date.NEAREST_DATE_START_OUTPUT_FORMAT

fun String.toAdvertisementDetailDate(): String = SimpleDateFormat(Date.INPUT_FORMAT, Locale.getDefault()).parse(this)?.let { SimpleDateFormat(Date.ADVERTISEMENT_DETAIL_OUTPUT_FORMAT, Locale.getDefault()).format(it) }.orEmpty()

fun ResponseNearestTimelineDto.toFormattedDate(): String = String.format(Date.MAIN_DATE_OUTPUT_FORMAT, this.month, this.day)

fun String.toFormattedDate(): String = String.format(
    Date.TIMELINE_OUTPUT_FORMAT,
    MonthType.fromNumber(this.split(".")[1].toInt()),
    this.split(".")[2].toInt()
)
