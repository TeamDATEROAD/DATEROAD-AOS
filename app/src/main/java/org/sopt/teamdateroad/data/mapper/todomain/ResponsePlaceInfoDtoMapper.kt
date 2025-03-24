package org.sopt.teamdateroad.data.mapper.todomain

import org.sopt.teamdateroad.data.dataremote.model.response.ResponsePlaceInfoDto
import org.sopt.teamdateroad.domain.model.PlaceInfo

fun ResponsePlaceInfoDto.toDomain(): PlaceInfo = PlaceInfo(
    placeName = this.placeName,
    addressName = this.address_name
)