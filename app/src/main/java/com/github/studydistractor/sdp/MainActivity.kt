package com.github.studydistractor.sdp

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.github.studydistractor.sdp.distraction.Distraction
import com.github.studydistractor.sdp.distraction.DistractionService
import com.github.studydistractor.sdp.history.HistoryInterface
import com.github.studydistractor.sdp.notification.NotificationService
import com.github.studydistractor.sdp.ui.NotificationPermission
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

    private lateinit var notificationService: NotificationService
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        notificationService = NotificationService(this)

        setContent{
            NotificationPermission()
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

    override fun onStop() {
        super.onStop()
        notificationService.showNotification(Distraction("test notif", "oue oue oue"))
    }

    override fun onResume() {
        super.onResume()
    }
}


