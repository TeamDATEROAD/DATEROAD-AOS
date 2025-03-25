package org.sopt.teamdateroad.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import org.sopt.teamdateroad.data.dataremote.service.AdvertisementService
import org.sopt.teamdateroad.data.dataremote.service.AuthService
import org.sopt.teamdateroad.data.dataremote.service.CourseService
import org.sopt.teamdateroad.data.dataremote.service.MyCourseService
import org.sopt.teamdateroad.data.dataremote.service.PlaceSearchService
import org.sopt.teamdateroad.data.dataremote.service.ProfileService
import org.sopt.teamdateroad.data.dataremote.service.TimelineService
import org.sopt.teamdateroad.data.dataremote.service.UserPointService
import org.sopt.teamdateroad.di.qualifier.DateRoad
import org.sopt.teamdateroad.di.qualifier.PlaceSearch
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun providesAdvertisementService(@DateRoad retrofit: Retrofit): AdvertisementService =
        retrofit.create(AdvertisementService::class.java)

    @Provides
    @Singleton
    fun providesAuthService(@DateRoad retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun providesCourseService(@DateRoad retrofit: Retrofit): CourseService =
        retrofit.create(CourseService::class.java)

    @Provides
    @Singleton
    fun providesMyCourseService(@DateRoad retrofit: Retrofit): MyCourseService =
        retrofit.create(MyCourseService::class.java)

    @Provides
    @Singleton
    fun providesProfileService(@DateRoad retrofit: Retrofit): ProfileService =
        retrofit.create(ProfileService::class.java)

    @Provides
    @Singleton
    fun provideTimelineService(@DateRoad retrofit: Retrofit): TimelineService =
        retrofit.create(TimelineService::class.java)

    @Provides
    @Singleton
    fun providesUserPointService(@DateRoad retrofit: Retrofit): UserPointService =
        retrofit.create(UserPointService::class.java)

    @Provides
    @Singleton
    fun providesPlaceSearchService(@PlaceSearch retrofit: Retrofit): PlaceSearchService =
        retrofit.create(PlaceSearchService::class.java)
}
