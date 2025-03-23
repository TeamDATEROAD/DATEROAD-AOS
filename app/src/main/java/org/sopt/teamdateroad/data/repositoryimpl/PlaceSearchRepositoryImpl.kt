package org.sopt.teamdateroad.data.repositoryimpl

import org.sopt.teamdateroad.data.dataremote.datasource.PlaceSearchDataSource
import org.sopt.teamdateroad.data.dataremote.model.response.ResponsePlaceSearchResultDto
import org.sopt.teamdateroad.data.mapper.todomain.toDomain
import org.sopt.teamdateroad.domain.model.PlaceSearchResult
import org.sopt.teamdateroad.domain.repository.PlaceSearchRepository
import retrofit2.Call
import javax.inject.Inject

class PlaceSearchRepositoryImpl @Inject constructor(private val placeSearchDataSource: PlaceSearchDataSource) : PlaceSearchRepository {
    override suspend fun getPlaceSearchResult(keyword: String): Call<ResponsePlaceSearchResultDto> {
        return placeSearchDataSource.getPlaceSearchResult(keyword)
    }
}