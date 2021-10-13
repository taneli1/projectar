package com.example.projectar.ui.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.projectar.R
import com.example.projectar.ui.screens.CHANNEL_ID
import kotlinx.coroutines.*

object NotificationBuilder {
    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "text",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Content desc"
            }

            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as
                    NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }

    fun sendTestNotification(context: Context) {
        val job = Job()
        val scope = CoroutineScope(job)

        scope.launch(Dispatchers.Default) {
            delay(7000)
            // when you want to send the notification
            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.goat)
                .setContentTitle("Order status")
                .setContentText("Your order has been received")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()

            NotificationManagerCompat.from(context).notify(1, notification)
        }
    }
}
