package org.sopt.teamdateroad.domain.usecase

import javax.inject.Inject
import javax.inject.Singleton
import org.sopt.teamdateroad.domain.model.NearestTimeline
import org.sopt.teamdateroad.domain.repository.TimelineRepository

@Singleton
class GetNearestTimelineUseCase @Inject constructor(
    private val timelineRepository: TimelineRepository
) {
    suspend operator fun invoke(): Result<NearestTimeline> = timelineRepository.getNearestTimeline()
}
