package com.github.studydistractor.sdp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import dagger.hilt.android.HiltAndroidApp

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
            val name = "Procrastination activity"
            val descriptionText = "Used to display notifications about procrastination activities"
            val channel = NotificationChannel(
                RandomActivityNotificationService.RANDOM_ACTIVITY_CHANNEL_ID,
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