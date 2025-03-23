package org.sopt.teamdateroad.domain.repository

import org.sopt.teamdateroad.data.dataremote.model.response.ResponsePlaceSearchResultDto
import org.sopt.teamdateroad.domain.model.PlaceSearchResult
import retrofit2.Call

interface PlaceSearchRepository {
    suspend fun getPlaceSearchResult(
        keyword: String,
    ): Call<ResponsePlaceSearchResultDto>
}