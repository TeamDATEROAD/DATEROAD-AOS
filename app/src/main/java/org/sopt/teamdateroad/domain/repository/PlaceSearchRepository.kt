package org.sopt.teamdateroad.domain.repository

import org.sopt.teamdateroad.domain.model.PlaceSearchResult

interface PlaceSearchRepository {
    suspend fun getPlaceSearchResult(
        keyword: String,
    ): Result<PlaceSearchResult>
}