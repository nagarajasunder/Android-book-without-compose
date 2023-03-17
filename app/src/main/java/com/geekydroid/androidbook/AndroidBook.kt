package com.geekydroid.androidbook

import android.app.ActivityManager
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import dagger.hilt.android.HiltAndroidApp

const val SAMPLE_NOTIFICATION_CHANNEL_ID = "SAMPLE_NOTIFICATION_CHANNEL_ID"
const val SAMPLE_NOTIFICATION_CHANNEL_NAME = "SAMPLE_NOTIFICATION_CHANNEL"
const val SAMPLE_NOTIFICATION_ID = -4
const val SERVICE_CHANNEL_ID = "NOTIFICATION_SERVICE_CHANNEL"
const val SERVICE_NOTIFICATION_ID = -5

@HiltAndroidApp
class AndroidBook : Application(), LifecycleEventObserver {

    private var serviceIntent: Intent? = null

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get()
            .lifecycle.addObserver(this)
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

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        //Toast.makeText(this,"Event $event",Toast.LENGTH_SHORT).show()
        when (event) {
            Lifecycle.Event.ON_CREATE -> {}
            Lifecycle.Event.ON_START -> {
                stopFloatingService()
            }
            Lifecycle.Event.ON_RESUME -> {
            }
            Lifecycle.Event.ON_PAUSE -> {}
            Lifecycle.Event.ON_STOP -> {
                startFloatingService()
            }
            Lifecycle.Event.ON_DESTROY -> {
                stopFloatingService()
            }
            Lifecycle.Event.ON_ANY -> {}
        }
    }

    private fun checkIfServiceIsRunning(): Boolean {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningServices = activityManager.getRunningServices(Int.MAX_VALUE)
        if (runningServices != null) {
            for (serviceInfo in runningServices) {
                val componentName = serviceInfo.service
                val serviceName = componentName.className
                if (serviceName == FloatingWidgetService::class.java.name) {
                    return true
                }
            }
        }
        return false
    }


    private fun stopFloatingService() {
        Toast.makeText(this,"stopFloatingService called ${checkIfServiceIsRunning()} ${serviceIntent != null}",Toast.LENGTH_SHORT).show()
        if (checkIfServiceIsRunning() && serviceIntent != null) {
            stopService(serviceIntent)
        }
    }

    private fun startFloatingService() {
        if (checkIfPermissionIsGranted()) {
            serviceIntent = Intent(this, FloatingWidgetService::class.java)
            startService(serviceIntent)
        }
    }

    private fun checkIfPermissionIsGranted() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        Settings.canDrawOverlays(this)
    } else {
        true
    }
}