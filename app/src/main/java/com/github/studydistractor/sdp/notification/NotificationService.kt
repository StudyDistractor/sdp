package com.github.studydistractor.sdp.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.github.studydistractor.sdp.R
import com.github.studydistractor.sdp.distraction.Distraction

class NotificationService(
    private val context: Context
) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE)
            as NotificationManager

    /**
     * Display notification, if the user click on the notification, it will display the activity
     * @param activity activity to be displayed
     */
    fun showNotification(distraction: Distraction) {
        val activityIntent = Intent(context, Distraction::class.java)
            .apply {
                putExtra("activity", distraction)
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
            .setContentText("${distraction.name} : ${distraction.description}")
//            .setContentIntent(activityPendingIntent)
            .build()

        notificationManager.notify(1, notification)

        Log.d("show notification", distraction.name!!)
    }

    companion object {
        const val RANDOM_ACTIVITY_CHANNEL_ID = "random_distraction_channel"
    }
}