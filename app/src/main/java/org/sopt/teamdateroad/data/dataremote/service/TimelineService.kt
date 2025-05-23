package org.sopt.teamdateroad.data.dataremote.service

import org.sopt.teamdateroad.data.dataremote.model.request.RequestTimelineDto
import org.sopt.teamdateroad.data.dataremote.model.response.ResponseEnrollTimelineDto
import org.sopt.teamdateroad.data.dataremote.model.response.ResponseNearestTimelineDto
import org.sopt.teamdateroad.data.dataremote.model.response.ResponseTimelineDetailDto
import org.sopt.teamdateroad.data.dataremote.model.response.ResponseTimelinesDto
import org.sopt.teamdateroad.data.dataremote.util.ApiConstraints.API
import org.sopt.teamdateroad.data.dataremote.util.ApiConstraints.DATES
import org.sopt.teamdateroad.data.dataremote.util.ApiConstraints.DATE_ID
import org.sopt.teamdateroad.data.dataremote.util.ApiConstraints.NEAREST
import org.sopt.teamdateroad.data.dataremote.util.ApiConstraints.TIME
import org.sopt.teamdateroad.data.dataremote.util.ApiConstraints.VERSION
import org.sopt.teamdateroad.data.dataremote.util.ApiConstraints.VERSION2
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TimelineService {
    @DELETE("$API/$VERSION/$DATES/{$DATE_ID}")
    suspend fun deleteTimeline(
        @Path(DATE_ID) timelineId: Int
    )

    @GET("$API/$VERSION2/$DATES/{$DATE_ID}")
    suspend fun getTimelineDetail(
        @Path(DATE_ID) timelineId: Int
    ): ResponseTimelineDetailDto

    @GET("$API/$VERSION/$DATES")
    suspend fun getTimelines(
        @Query(TIME) timelineTimeType: String
    ): ResponseTimelinesDto

    @GET("$API/$VERSION/$DATES/$NEAREST")
    suspend fun getNearestTimeline(): ResponseNearestTimelineDto

    @POST("$API/$VERSION2/$DATES")
    suspend fun postTimeline(
        @Body requestTimelineDto: RequestTimelineDto
    ): ResponseEnrollTimelineDto
}
