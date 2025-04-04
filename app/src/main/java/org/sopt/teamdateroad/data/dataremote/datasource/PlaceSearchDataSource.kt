package org.sopt.teamdateroad.data.dataremote.datasource

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.sopt.teamdateroad.data.dataremote.model.response.ResponsePlaceInfoDto

interface PlaceSearchDataSource {
    suspend fun getPlaceSearchResult(
        keyword: String
    ): Flow<PagingData<ResponsePlaceInfoDto>>
}
