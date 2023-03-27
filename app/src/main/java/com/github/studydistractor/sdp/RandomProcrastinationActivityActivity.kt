package com.github.studydistractor.sdp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

/**
 * Activity that has a button to request a random activity
 */
class RandomProcrastinationActivityActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val service = RandomActivityNotificationService(applicationContext)
        setContent {

            DisplayRandomActivity(service)
        }
        }
    }

    @Composable
    fun DisplayRandomActivity(service: RandomActivityNotificationService) {
        val currentActivity = ProcrastinationActivity("Test", "test description")

        val context = LocalContext.current
        var hasNotificationPermission by remember {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                mutableStateOf(
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_DENIED
                )
            }  else mutableStateOf(true)
        }

        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted ->
                hasNotificationPermission = isGranted
            }
        )

        Column(modifier = Modifier.fillMaxSize()) {

            Button(onClick = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }) {
                Text("Request permission")
            }
            Button(onClick = {
                if(hasNotificationPermission) {
                    service.showNotification(currentActivity)
                }
            }) {
                Text("Show me notification")
            }
        }
    }
