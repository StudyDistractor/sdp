package com.github.studydistractor.sdp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.Snapshot
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * Activity that has a button to request a random activity
 */
@AndroidEntryPoint(AppCompatActivity::class)
class RandomProcrastinationActivityActivity : Hilt_RandomProcrastinationActivityActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val notificationService = RandomActivityNotificationService(applicationContext)
        setContent {
            DisplayRandomActivity(notificationService)
        }
    }

    /**
     * This function will handle 2 buttons, the first one to ask permission for notification and
     * the second one will display a notification.
     *
     * @Param service, the notification service that will post
     */
    @Composable
    fun DisplayRandomActivity(service: RandomActivityNotificationService) {
        val currentActivity = ProcrastinationActivity("Test", "test description")

        val context = LocalContext.current
        var hasNotificationPermission by remember {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                mutableStateOf(
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_DENIED
                )
            } else mutableStateOf(true)
        }

        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted ->
                hasNotificationPermission = isGranted
            }
        )

        Column(modifier = Modifier.fillMaxSize()) {

            Button(
                onClick = {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                },
                modifier = Modifier.testTag("permission")
            ) {
                Text("Request permission")
            }

            Button(
                onClick = {
                    if (hasNotificationPermission) {
                        service.showNotification(currentActivity)
                    }
                },
                modifier = Modifier.testTag("notification")
                ) {
                Text("Show me notification")
            }
        }
    }
}
