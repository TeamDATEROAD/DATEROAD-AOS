package org.sopt.teamdateroad.data.repositoryimpl

import javax.inject.Inject
import org.sopt.teamdateroad.data.dataremote.datasource.PlaceSearchDataSource
import org.sopt.teamdateroad.data.mapper.todomain.toDomain
import org.sopt.teamdateroad.domain.model.PlaceSearchResult
import org.sopt.teamdateroad.domain.repository.PlaceSearchRepository

class PlaceSearchRepositoryImpl @Inject constructor(private val placeSearchDataSource: PlaceSearchDataSource) : PlaceSearchRepository {
    override suspend fun getPlaceSearchResult(keyword: String): Result<PlaceSearchResult> {
        return placeSearchDataSource.getPlaceSearchResult(keyword).map { responsePlaceSearchResultDto ->
            responsePlaceSearchResultDto.toDomain()
        }
    }
}
