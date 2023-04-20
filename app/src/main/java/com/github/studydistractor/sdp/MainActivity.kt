package com.github.studydistractor.sdp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.github.studydistractor.sdp.history.HistoryInterface
import com.github.studydistractor.sdp.distraction.DistractionService
import com.github.studydistractor.sdp.ui.theme.StudyDistractorTheme
import com.github.studydistractor.sdp.user.UserService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint(AppCompatActivity::class)
class MainActivity : Hilt_MainActivity() {
    @Inject
    lateinit var historyInterface : HistoryInterface

    @Inject
    lateinit var distractionService : DistractionService

    @Inject
    lateinit var userService: UserService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            StudyDistractorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    StudyDistractorApp(
                        historyInterface = historyInterface,
                        distractionService = distractionService,
                        userService = userService
                    )
                }
            }
        }
    }
}