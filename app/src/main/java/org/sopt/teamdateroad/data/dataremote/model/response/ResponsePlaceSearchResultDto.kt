package org.sopt.teamdateroad.data.dataremote.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponsePlaceSearchResultDto(
    @SerialName("documents")
    val placeInfos: List<ResponsePlaceInfoDto>
)