package com.geekydroid.androidbook

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.geekydroid.androidbook.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var serviceIntent: Intent? = null
    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                //showWidget()
            }
        }

    private fun showWidget() {
        serviceIntent = Intent(this, FloatingWidgetService::class.java)
        startService(serviceIntent)
    }

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)

        initUI()


    }

    @SuppressLint("InlinedApi")
    private fun initUI() {
        binding.btnShowFloatingWidget.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(this)) {
                Toast.makeText(this@MainActivity, "Launching Widget", Toast.LENGTH_SHORT).show()
                showWidget()
            } else {
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:${packageName}")
                )
                resultLauncher.launch(intent)
            }
        }
        binding.btnHideFloatingWidget.setOnClickListener {
            serviceIntent?.let { intent ->
                stopService(intent)
            }
        }
        binding.btnCheckServiceStatus.setOnClickListener {
            if (checkIfServiceIsRunning()) {
                Toast.makeText(this,"Service is running",Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this,"Service not running",Toast.LENGTH_SHORT).show()
            }
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


}