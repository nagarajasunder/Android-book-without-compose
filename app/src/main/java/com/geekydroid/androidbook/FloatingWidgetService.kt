package com.geekydroid.androidbook

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.geekydroid.androidbook.databinding.FloatingWidgetViewBinding


class FloatingWidgetService : Service() {


    private var windowManager: WindowManager? = null
    private lateinit var binding: FloatingWidgetViewBinding

    private fun isViewCollapsed(): Boolean = binding.collapsedView.visibility == View.GONE


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        val layoutInflater:LayoutInflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.floating_widget_view,
            null,
            false
        )
        val layoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                WindowManager.LayoutParams.TYPE_PHONE
            },
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )



        binding.rootLayout.setOnTouchListener(object : View.OnTouchListener {
            var initialX = 0
            var initialY = 0
            var initialTouchX = 0f
            var initialTouchY = 0f
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        initialX = layoutParams.x
                        initialY = layoutParams.y
                        initialTouchX = event.rawX
                        initialTouchY = event.rawY
                    }
                    MotionEvent.ACTION_MOVE -> {
                        layoutParams.x = initialX + (event.rawX - initialTouchX).toInt()
                        layoutParams.y = initialY + (event.rawY - initialTouchY).toInt()
                        windowManager?.updateViewLayout(binding.rootLayout, layoutParams)
                    }
                    MotionEvent.ACTION_UP -> {
                        val Xdiff = event.rawX - initialTouchX
                        val YDiff = event.rawY - initialTouchY
                        if (Xdiff < 10 && YDiff < 10) {
                            if (isViewCollapsed()) {
                                expandView()
                            } else {
                                collapsedView()
                            }
                        }
                    }
                }
                return true
            }

        })
        layoutParams.gravity = Gravity.TOP or Gravity.START
        layoutParams.x = 0
        layoutParams.y = 100
        windowManager?.addView(binding.root, layoutParams)


    }



    private fun collapsedView() {
        binding.collapsedView.visibility = View.GONE
    }

    private fun expandView() {
        binding.collapsedView.visibility = View.VISIBLE
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this,"Stop Service called",Toast.LENGTH_SHORT).show()
        removeWindowManager()
        stopSelf()
    }

    private fun removeWindowManager() {
        windowManager?.removeView(binding.root)
    }


}