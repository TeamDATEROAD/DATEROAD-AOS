package org.sopt.teamdateroad.domain.usecase

import javax.inject.Inject
import javax.inject.Singleton
import org.sopt.teamdateroad.domain.model.Timeline
import org.sopt.teamdateroad.domain.repository.TimelineRepository
import org.sopt.teamdateroad.domain.type.TimelineTimeType

@Singleton
class GetTimelinesUseCase @Inject constructor(
    private val timelineRepository: TimelineRepository
) {
    suspend operator fun invoke(timelineTimeType: TimelineTimeType): Result<List<Timeline>> = timelineRepository.getTimelines(timelineTimeType = timelineTimeType)
}
