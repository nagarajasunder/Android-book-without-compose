package com.geekydroid.androidbook

import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.databinding.DataBindingUtil
import com.geekydroid.androidbook.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import org.joda.time.DateTime
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            Toast.makeText(this, "Notification Permission Granted $result", Toast.LENGTH_SHORT)
                .show()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)

        initUi()

    }

    private fun initUi() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            permissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
//        }
        binding.btnPostNotification.setOnClickListener {
            postNotification()
        }
        binding.btnStartService.setOnClickListener {
            val intent = Intent(this, SampleService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent)
            } else {
                startService(intent)
            }
        }
    }

    private fun postNotification() {
        val notification = NotificationCompat.Builder(this, SAMPLE_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Sample Notification")
            .setContentText(getContentText())
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(SAMPLE_NOTIFICATION_ID, notification.build())
    }

    private fun getContentText(): String {
        val now = LocalDateTime.now()
        val formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss -a")
        return "Notification Showing in ${formatter.print(now)}"
    }

}