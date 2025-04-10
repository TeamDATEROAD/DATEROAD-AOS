package org.sopt.teamdateroad.domain.usecase

import javax.inject.Inject
import javax.inject.Singleton
import org.sopt.teamdateroad.domain.repository.TimelineRepository

@Singleton
class DeleteTimelineUseCase @Inject constructor(
    private val timelineRepository: TimelineRepository
) {
    suspend operator fun invoke(timelineId: Int): Result<Unit> = timelineRepository.deleteTimeline(timelineId = timelineId)
}
