package org.sopt.teamdateroad.data.dataremote.service

import org.sopt.teamdateroad.data.dataremote.model.response.ResponsePlaceSearchResultDto
import org.sopt.teamdateroad.data.dataremote.util.PlaceSearch.LOCAL_SEARCH_KEYWORD_JSON
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceSearchService {
    @GET(LOCAL_SEARCH_KEYWORD_JSON)
    suspend fun getPlaceSearchResult(
        @Query("query") keyword: String,
    ): ResponsePlaceSearchResultDto
}