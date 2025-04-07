package org.sopt.teamdateroad

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.gms.ads.MobileAds
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import org.sopt.teamdateroad.BuildConfig.KAKAO_NATIVE_APP_KEY
import org.sopt.teamdateroad.presentation.util.amplitude.AmplitudeUtils.initAmplitude
import timber.log.Timber

@HiltAndroidApp
class DateRoadApp : Application() {
    override fun onCreate() {
        super.onCreate()

        setTimber()
        setDarkMode()
        setKakao()
        setAds()
        initAmplitude(applicationContext)
    }

    private fun setTimber() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    private fun setDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun setKakao() {
        KakaoSdk.init(this, KAKAO_NATIVE_APP_KEY)
    }

    private fun setAds() {
        MobileAds.initialize(this)
    }
}
