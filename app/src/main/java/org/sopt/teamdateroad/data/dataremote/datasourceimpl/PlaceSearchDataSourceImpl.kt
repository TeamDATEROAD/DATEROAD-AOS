package org.sopt.teamdateroad.data.dataremote.datasourceimpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import org.sopt.teamdateroad.data.dataremote.datasource.PlaceSearchDataSource
import org.sopt.teamdateroad.data.dataremote.model.response.ResponsePlaceInfoDto
import org.sopt.teamdateroad.data.dataremote.service.PlaceSearchService
import org.sopt.teamdateroad.presentation.util.paging.PlaceSearchPagingSource
import org.sopt.teamdateroad.presentation.util.paging.PlaceSearchPagingSource.Companion.MAX_SIZE
import org.sopt.teamdateroad.presentation.util.paging.PlaceSearchPagingSource.Companion.PAGE_SIZE

class PlaceSearchDataSourceImpl @Inject constructor(private val placeSearchService: PlaceSearchService) : PlaceSearchDataSource {
    override suspend fun getPlaceSearchResult(keyword: String): Result<Flow<PagingData<ResponsePlaceInfoDto>>> {
        return runCatching {
            Pager(
                config = PagingConfig(pageSize = PAGE_SIZE, maxSize = MAX_SIZE),
                pagingSourceFactory = {
                    PlaceSearchPagingSource(
                        keyword = keyword,
                        placeSearchService = placeSearchService
                    )
                }
            ).flow
        }
    }
}
