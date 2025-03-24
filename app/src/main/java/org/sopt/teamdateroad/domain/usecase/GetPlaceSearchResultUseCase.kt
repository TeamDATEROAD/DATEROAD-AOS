package org.sopt.teamdateroad.domain.usecase

import org.sopt.teamdateroad.domain.model.PlaceSearchResult
import org.sopt.teamdateroad.domain.repository.PlaceSearchRepository
import javax.inject.Inject

class GetPlaceSearchResultUseCase @Inject constructor(
    private val placeSearchRepository: PlaceSearchRepository
) {
    suspend operator fun invoke(keyword: String): Result<PlaceSearchResult> = placeSearchRepository.getPlaceSearchResult(keyword)
}