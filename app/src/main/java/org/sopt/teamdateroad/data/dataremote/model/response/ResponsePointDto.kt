package org.sopt.teamdateroad.data.dataremote.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponsePointDto(
    @SerialName("point")
    val point: Int,
    @SerialName("description")
    val description: String,
    @SerialName("createdAt")
    val createdAt: String
)
