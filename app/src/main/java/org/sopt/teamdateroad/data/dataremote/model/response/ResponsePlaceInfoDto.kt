package org.sopt.teamdateroad.data.dataremote.model.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ResponsePlaceInfoDto(
    @SerializedName("place_name")
    val placeName: String,
    @SerializedName("address_name")
    val address_name: String
)
