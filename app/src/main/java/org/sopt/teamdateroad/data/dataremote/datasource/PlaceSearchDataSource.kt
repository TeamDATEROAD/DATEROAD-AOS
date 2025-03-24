package org.sopt.teamdateroad.data.dataremote.datasource

import org.sopt.teamdateroad.data.dataremote.model.response.ResponsePlaceSearchResultDto

interface PlaceSearchDataSource {
    suspend fun getPlaceSearchResult(
        keyword: String,
    ): Result<ResponsePlaceSearchResultDto>
}