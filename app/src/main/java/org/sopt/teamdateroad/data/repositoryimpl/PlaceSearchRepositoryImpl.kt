package org.sopt.teamdateroad.data.repositoryimpl

import androidx.paging.PagingData
import androidx.paging.map
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.sopt.teamdateroad.data.dataremote.datasource.PlaceSearchDataSource
import org.sopt.teamdateroad.data.mapper.todomain.toDomain
import org.sopt.teamdateroad.domain.model.PlaceInfo
import org.sopt.teamdateroad.domain.repository.PlaceSearchRepository

class PlaceSearchRepositoryImpl @Inject constructor(private val placeSearchDataSource: PlaceSearchDataSource) : PlaceSearchRepository {
    override suspend fun getPlaceSearchResult(keyword: String): Flow<PagingData<PlaceInfo>> {
        return placeSearchDataSource.getPlaceSearchResult(keyword).map { pagingData ->
            pagingData.map { responsePlaceInfoDto ->
                responsePlaceInfoDto.toDomain()
            }
        }
    }
}
