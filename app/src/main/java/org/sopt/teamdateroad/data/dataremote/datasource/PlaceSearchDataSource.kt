package org.sopt.teamdateroad.data.dataremote.datasource

import org.sopt.teamdateroad.data.dataremote.model.response.ResponsePlaceSearchResultDto
import retrofit2.Call

interface PlaceSearchDataSource {
    suspend fun getPlaceSearchResult(
        keyword: String,
    ): Call<ResponsePlaceSearchResultDto>
}