package org.sopt.teamdateroad.domain.usecase

import androidx.paging.PagingData
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import org.sopt.teamdateroad.domain.model.PlaceInfo
import org.sopt.teamdateroad.domain.repository.PlaceSearchRepository
import javax.inject.Singleton

@Singleton
class GetPlaceSearchResultUseCase @Inject constructor(
    private val placeSearchRepository: PlaceSearchRepository
) {
    suspend operator fun invoke(keyword: String): Result<Flow<PagingData<PlaceInfo>>> = placeSearchRepository.getPlaceSearchResult(keyword)
}
