package org.sopt.teamdateroad.data.dataremote.datasourceimpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import org.sopt.teamdateroad.data.dataremote.datasource.PlaceSearchDataSource
import org.sopt.teamdateroad.data.dataremote.model.response.ResponsePlaceInfoDto
import org.sopt.teamdateroad.data.dataremote.service.PlaceSearchService
import org.sopt.teamdateroad.presentation.ui.enroll.component.MAX_SIZE
import org.sopt.teamdateroad.presentation.ui.enroll.component.PAGE_SIZE

class PlaceSearchDataSourceImpl @Inject constructor(private val placeSearchService: PlaceSearchService) : PlaceSearchDataSource {
    override suspend fun getPlaceSearchResult(keyword: String): Flow<PagingData<ResponsePlaceInfoDto>> {
        return Pager(
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

class PlaceSearchPagingSource(
    private val keyword: String,
    private val placeSearchService: PlaceSearchService
) : PagingSource<Int, ResponsePlaceInfoDto>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResponsePlaceInfoDto> {
        val page = params.key ?: INITIAL_PAGE

        return runCatching {
            val placeSearchResult = placeSearchService.getPlaceSearchResult(
                keyword = keyword,
                page = page,
                size = PAGE_SIZE
            )

            LoadResult.Page(
                data = placeSearchResult.placeInfos,
                prevKey = if (page == INITIAL_PAGE) null else page - PAGE_OFFSET,
                nextKey = if (placeSearchResult.meta.isEnd) null else page + PAGE_OFFSET
            )
        }.getOrElse { throwable ->
            LoadResult.Error(throwable)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ResponsePlaceInfoDto>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(PAGE_OFFSET)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(PAGE_OFFSET)
        }
    }

    companion object {
        private const val INITIAL_PAGE = 1
        private const val PAGE_OFFSET = 1
    }
}
