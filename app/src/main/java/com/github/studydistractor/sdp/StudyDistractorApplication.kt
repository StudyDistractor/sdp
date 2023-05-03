package com.github.studydistractor.sdp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import dagger.hilt.android.HiltAndroidApp
import android.content.Context
import com.github.studydistractor.sdp.notification.NotificationService

@HiltAndroidApp
class StudyDistractorApplication : Application(){
    override fun onCreate() {
        super.onCreate()
       createNotificationChannel()
    }

    /**
     * Create the channel to display notification
     */
    private fun createNotificationChannel() {
       if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "random distractions"
            val descriptionText = "Used to display notifications about distractions"
            val channel = NotificationChannel(
                NotificationService.RANDOM_ACTIVITY_CHANNEL_ID,
                name,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}