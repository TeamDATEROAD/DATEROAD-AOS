package org.sopt.teamdateroad.data.dataremote.service

import org.sopt.teamdateroad.data.dataremote.model.response.ResponsePlaceSearchResultDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceSearchService {
    @GET("v2/local/search/keyword.json")
    fun getPlaceSearchResult(
        @Query("query") keyword: String,
    ): Call<ResponsePlaceSearchResultDto>
}