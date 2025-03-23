package org.sopt.teamdateroad.data.dataremote.datasourceimpl

import org.sopt.teamdateroad.data.dataremote.datasource.PlaceSearchDataSource
import org.sopt.teamdateroad.data.dataremote.model.response.ResponsePlaceSearchResultDto
import org.sopt.teamdateroad.data.dataremote.service.PlaceSearchService
import retrofit2.Call
import javax.inject.Inject

class PlaceSearchDataSourceImpl @Inject constructor(private val placeSearchService: PlaceSearchService) : PlaceSearchDataSource {
    override suspend fun getPlaceSearchResult(keyword: String): Call<ResponsePlaceSearchResultDto> {
        return placeSearchService.getPlaceSearchResult(keyword)
    }
}