package org.sopt.teamdateroad.domain.usecase

import org.sopt.teamdateroad.domain.repository.UserPointRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostAdsPointUseCase @Inject constructor(
    private val userPointRepository: UserPointRepository
) {
    suspend operator fun invoke() = userPointRepository.postAdsPoint()
}
