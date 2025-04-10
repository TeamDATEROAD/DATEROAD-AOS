package org.sopt.teamdateroad.data.dataremote.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseNearestTimelineDto(
    @SerialName("dateId")
    val timelineId: Int,
    @SerialName("dDay")
    val dDay: Int,
    @SerialName("dateName")
    val dateName: String,
    @SerialName("month")
    val month: Int,
    @SerialName("day")
    val day: Int,
    @SerialName("startAt")
    val startAt: String
)
