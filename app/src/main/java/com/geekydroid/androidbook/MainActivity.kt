package com.geekydroid.androidbook

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
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

        initView()
    }

    private fun isToday(initialDate: DateTime): Boolean {
        return DateTime().withTime(0,0,0,0).millis == initialDate.millis
    }

    private fun initView() {
        val dtf = DateTimeFormat.forPattern("yyyy-MM-dd")
        val startDate = dtf.parseDateTime(attachmentDate)
        Log.d("TAG", "initView: $startDate")
        val totalCalendarDays = Days.daysBetween(startDate,DateTime().withTime(0,0,0,0)).days + 2
        binding.horizontalDatePicker.init(totalCalendarDays+2,totalCalendarDays,initialDate)

        binding.ivPreviousDay.setOnClickListener {
            binding.horizontalDatePicker.setDate(initialDate.minusDays(1))
        }

        binding.ivNextDay.setOnClickListener {
            if (!isToday(initialDate)) {
                binding.horizontalDatePicker.setDate(initialDate.plusDays(1))
            }
        }
    }

}