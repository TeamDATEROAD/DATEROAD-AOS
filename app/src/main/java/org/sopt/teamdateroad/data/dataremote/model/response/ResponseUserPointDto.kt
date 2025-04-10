package org.sopt.teamdateroad.data.dataremote.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseUserPointDto(
    @SerialName("name")
    val name: String,
    @SerialName("point")
    val point: Int,
    @SerialName("image")
    val image: String?
)
