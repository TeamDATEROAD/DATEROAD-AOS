package org.sopt.teamdateroad.presentation.ui.navigator

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.sopt.teamdateroad.domain.repository.PlaceSearchRepository
import org.sopt.teamdateroad.presentation.ui.splash.SplashScreen
import org.sopt.teamdateroad.ui.theme.DATEROADTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var placeSearchRepository: PlaceSearchRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navigator: MainNavigator = rememberMainNavigator()
            var showSplash by remember { mutableStateOf(true) }

            DATEROADTheme {
                LaunchedEffect(Unit) {
                    // Display splash screen for a brief period
                    delay(SPLASH_SCREEN_DELAY)
                    showSplash = false

                    launch(Dispatchers.IO) {
                        try {
                            val placeSearchResult = placeSearchRepository.getPlaceSearchResult("치킨").execute().body()
                            println(placeSearchResult)
                        } catch (e: Exception) {
                            println("Error: ${e}")
                        }
                    }
                }

                if (showSplash) {
                    SplashScreen()
                } else {
                    MainScreen(navigator = navigator)
                }
            }
        }
    }

    companion object {
        const val SPLASH_SCREEN_DELAY = 2000L
    }
}
