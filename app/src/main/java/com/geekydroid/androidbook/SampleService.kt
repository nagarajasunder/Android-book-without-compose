package com.geekydroid.androidbook

import android.Manifest
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE
import androidx.core.app.NotificationManagerCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SampleService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private lateinit var notificationManager: NotificationManagerCompat

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val notification = createNotification()
        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show()
        startForeground(SERVICE_NOTIFICATION_ID, notification.build())

        doWork()
        Toast.makeText(this, "Service Ended", Toast.LENGTH_SHORT).show()
        stopSelf()

        return START_NOT_STICKY
    }

    private fun doWork() {
        while (true){

        }
    }

    private fun updateNotification() {


        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        notificationManager.notify(SERVICE_NOTIFICATION_ID, createNotification().build())

    }


    private fun createNotification(): NotificationCompat.Builder {

        return NotificationCompat.Builder(this, SAMPLE_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentText("Sample Service")
            .setContentText("Service running in ${System.currentTimeMillis()}")
            .setForegroundServiceBehavior(FOREGROUND_SERVICE_IMMEDIATE)
    }
}