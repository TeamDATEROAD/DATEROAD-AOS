package org.sopt.teamdateroad.presentation.util.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.sopt.teamdateroad.data.dataremote.service.PlaceSearchService
import org.sopt.teamdateroad.data.mapper.todomain.toDomain
import org.sopt.teamdateroad.domain.model.PlaceInfo

class PlaceSearchResultPagingSource(
    private val keyword: String,
    private val placeSearchService: PlaceSearchService
) : PagingSource<Int, PlaceInfo>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PlaceInfo> {
        val page = params.key ?: INITIAL_PAGE

        return runCatching {
            val placeSearchResult = placeSearchService.getPlaceSearchResult(
                keyword = keyword,
                page = page,
                size = PAGE_SIZE
            )

            LoadResult.Page(
                data = placeSearchResult.placeInfos.toDomain(),
                prevKey = if (page == INITIAL_PAGE) null else page - PAGE_OFFSET,
                nextKey = if (placeSearchResult.meta.isEnd) null else page + PAGE_OFFSET
            )
        }.getOrElse { throwable ->
            LoadResult.Error(throwable)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PlaceInfo>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(PAGE_OFFSET)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(PAGE_OFFSET)
        }
    }

    companion object {
        private const val INITIAL_PAGE = 1
        private const val PAGE_SIZE = 10
        private const val PAGE_OFFSET = 1
    }
}
