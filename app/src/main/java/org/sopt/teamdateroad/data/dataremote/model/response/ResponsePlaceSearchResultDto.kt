package org.sopt.teamdateroad.data.dataremote.model.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ResponsePlaceSearchResultDto(
    @SerializedName("documents")
    val placeInfos: List<ResponsePlaceInfoDto>
)