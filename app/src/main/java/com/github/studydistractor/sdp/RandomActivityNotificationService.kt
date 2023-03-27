package com.github.studydistractor.sdp

import android.Manifest
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

/**
 * This class represent the notification service that shows random activities notification
 */
class RandomActivityNotificationService(
    private val context : Context
) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE)
            as NotificationManager

    /**
     * Display notification, if the user click on the notification, it will display the activity
     * @param activity activity to be displayed
     */
    fun showNotification(activity: ProcrastinationActivity) {
        val activityIntent = Intent(context, ProcrastinationActivityActivity::class.java)
            .apply {
                putExtra("activity", activity)
            }

        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, RANDOM_ACTIVITY_CHANNEL_ID)
            .setSmallIcon(R.drawable.magic_button)
            .setContentTitle("Procrastination activity")
            .setContentText("${activity.name} : ${activity.description}")
            .setContentIntent(activityPendingIntent)
            .build()

        notificationManager.notify(1, notification)
    }

    companion object {
        const val RANDOM_ACTIVITY_CHANNEL_ID = "random_activity_channel"
    }
}