package org.sopt.teamdateroad.data.dataremote.service

import org.sopt.teamdateroad.data.dataremote.model.response.ResponsePlaceSearchResultDto
import org.sopt.teamdateroad.data.dataremote.util.PlaceSearch.PLACE_SEARCH_URL
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceSearchService {
    @GET(PLACE_SEARCH_URL)
    suspend fun getPlaceSearchResult(
        @Query("query") keyword: String,
    ): ResponsePlaceSearchResultDto
}