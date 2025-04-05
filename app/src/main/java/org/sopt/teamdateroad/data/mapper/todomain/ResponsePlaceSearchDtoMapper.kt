package org.sopt.teamdateroad.data.mapper.todomain

import org.sopt.teamdateroad.data.dataremote.model.response.ResponsePlaceSearchResultDto
import org.sopt.teamdateroad.domain.model.PlaceSearchResult

fun ResponsePlaceSearchResultDto.toDomain(): PlaceSearchResult = PlaceSearchResult(
    placeInfos = this.placeInfos.map { responsePlaceInfoDto -> responsePlaceInfoDto.toDomain() },
    isEnd = this.meta.isEnd
)
