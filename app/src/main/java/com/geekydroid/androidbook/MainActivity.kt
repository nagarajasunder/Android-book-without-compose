package com.geekydroid.androidbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.geekydroid.androidbook.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        setContentView(binding.root)

        initializeViews()
    }

    private fun initializeViews() {
        val itemList = generateItems()
        binding.dateSelectorRecyclerView.init(itemList)
        binding.ivPrevious.setOnClickListener {
            binding.dateSelectorRecyclerView.let {
                it.setSelection(it.getCurrentSelectedItemPosition()-1)
            }
        }
        binding.ivNext.setOnClickListener {
            binding.dateSelectorRecyclerView.let {
                it.setSelection(it.getCurrentSelectedItemPosition()+1)
            }
        }

    }

    private fun generateItems(): List<CalendarItem> {
        val itemList = mutableListOf<CalendarItem>()
        var start = -20
        repeat(30) {
            itemList.add(CalendarItem("Num\n$start",false))
            start++
        }

        return itemList
    }
}