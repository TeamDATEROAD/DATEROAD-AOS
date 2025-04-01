package org.sopt.teamdateroad.domain.usecase

import javax.inject.Inject
import org.sopt.teamdateroad.domain.model.PlaceSearchResult
import org.sopt.teamdateroad.domain.repository.PlaceSearchRepository

class GetPlaceSearchResultUseCase @Inject constructor(
    private val placeSearchRepository: PlaceSearchRepository
) {
    suspend operator fun invoke(keyword: String, page: Int, size: Int): Result<PlaceSearchResult> = placeSearchRepository.getPlaceSearchResult(keyword, page, size)
}
