package com.geekydroid.androidbook

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.geekydroid.androidbook.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.format.DateTimeFormat

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    private val attachmentDate = "2022-10-20"
    private val initialDate = DateTime().withTime(0,0,0,0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        setContentView(binding.root)

    }


}