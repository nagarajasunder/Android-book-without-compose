package com.geekydroid.androidbook

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.geekydroid.androidbook.customviews.CustomLayout
import com.geekydroid.androidbook.customviews.OwnCustomView
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

    }
}