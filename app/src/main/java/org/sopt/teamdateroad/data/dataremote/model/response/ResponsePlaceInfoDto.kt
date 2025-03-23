package org.sopt.teamdateroad.data.dataremote.model.response

import kotlinx.serialization.SerialName

data class ResponsePlaceInfoDto(
    @SerialName("place_name")
    val placeName: String,
    @SerialName("address_name")
    val addressName: String,
)