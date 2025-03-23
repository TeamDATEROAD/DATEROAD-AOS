package org.sopt.teamdateroad.data.dataremote.model.response

import kotlinx.serialization.SerialName

data class ResponsePlaceSearchResultDto(
    @SerialName("documents")
    val placeInfos: List<ResponsePlaceInfoDto>
)