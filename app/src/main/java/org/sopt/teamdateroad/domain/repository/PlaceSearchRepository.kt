package org.sopt.teamdateroad.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.sopt.teamdateroad.domain.model.PlaceInfo

interface PlaceSearchRepository {
    suspend fun getPlaceSearchResult(
        keyword: String
    ): Flow<PagingData<PlaceInfo>>
}
