package org.sopt.teamdateroad.data.dataremote.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseTimelineDto(
    @SerialName("dateId")
    val timelineId: Int,
    @SerialName("title")
    val title: String,
    @SerialName("date")
    val date: String,
    @SerialName("city")
    val city: String,
    @SerialName("tags")
    val tags: List<ResponseTagDto>,
    @SerialName("dDay")
    val dDay: Int
)
