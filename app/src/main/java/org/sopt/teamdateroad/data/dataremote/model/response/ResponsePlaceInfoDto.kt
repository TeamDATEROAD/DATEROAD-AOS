package org.sopt.teamdateroad.data.dataremote.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponsePlaceInfoDto(
    @SerialName("place_name")
    val placeName: String,
    @SerialName("address_name")
    val addressName: String,
)