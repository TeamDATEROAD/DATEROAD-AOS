package org.sopt.teamdateroad.presentation.util.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.sopt.teamdateroad.domain.model.PlaceInfo
import org.sopt.teamdateroad.domain.usecase.GetPlaceSearchResultUseCase

class PlaceSearchPagingSource(
    private val keyword: String,
    private val getPlaceSearchResultUseCase: GetPlaceSearchResultUseCase
) : PagingSource<Int, PlaceInfo>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PlaceInfo> {
        val page = params.key ?: INITIAL_PAGE

        return runCatching {
            val placeSearchResult = getPlaceSearchResultUseCase(
                keyword = keyword,
                page = page,
                size = PAGE_SIZE
            ).getOrThrow()

            LoadResult.Page(
                data = placeSearchResult.placeInfos,
                prevKey = if (page == INITIAL_PAGE) null else page - PAGE_OFFSET,
                nextKey = if (placeSearchResult.isEnd) null else page + PAGE_OFFSET
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
        private const val PAGE_OFFSET = 1
        const val PAGE_SIZE = 10
        const val MAX_SIZE = 50
    }
}
