package org.sopt.teamdateroad.data.mapper.todomain

import org.sopt.teamdateroad.data.dataremote.model.response.ResponsePlaceInfoDto
import org.sopt.teamdateroad.domain.model.PlaceInfo

fun List<ResponsePlaceInfoDto>.toDomain(): List<PlaceInfo> =
    this.map { responsePlaceInfoDto -> responsePlaceInfoDto.toDomain() }
