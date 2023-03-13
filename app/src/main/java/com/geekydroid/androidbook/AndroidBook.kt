package com.geekydroid.androidbook

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import dagger.hilt.android.HiltAndroidApp

const val SAMPLE_NOTIFICATION_CHANNEL_ID = "SAMPLE_NOTIFICATION_CHANNEL_ID"
const val SAMPLE_NOTIFICATION_CHANNEL_NAME = "SAMPLE_NOTIFICATION_CHANNEL"
const val SAMPLE_NOTIFICATION_ID = -4
const val SERVICE_CHANNEL_ID = "NOTIFICATION_SERVICE_CHANNEL"
const val SERVICE_NOTIFICATION_ID = -5

@HiltAndroidApp
class AndroidBook : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                SAMPLE_NOTIFICATION_CHANNEL_ID,
                SAMPLE_NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "Sample Notification Channel"
            val notificationManger = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManger.createNotificationChannel(channel)
        }
    }
}