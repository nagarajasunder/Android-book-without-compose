package com.geekydroid.androidbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SmoothScroller
import com.geekydroid.androidbook.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var adapter: TaskAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)
        setView()
    }

    private fun setView() {
        binding.taskRecyclerView.setHasFixedSize(true)
        binding.taskRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        adapter = TaskAdapter(TaskGenerator.getTasks())
        binding.taskRecyclerView.adapter = adapter
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.taskRecyclerView)


        binding.btnScroll.setOnClickListener {
            val position = binding.edScrollPosition.text.toString()
            if (position.isNotEmpty()) {
                binding.taskRecyclerView.smoothScrollToPosition(position.toInt())
            }
        }

        object : SmoothScroller(){
            override fun onStart() {

            }

            override fun onStop() {

            }

            override fun onSeekTargetStep(
                dx: Int,
                dy: Int,
                state: RecyclerView.State,
                action: Action
            ) {

            }

            override fun onTargetFound(
                targetView: View,
                state: RecyclerView.State,
                action: Action
            ) {

            }

        }
    }
}


object TaskGenerator {

    private val taskList = mutableListOf<TaskItem>()

    init {
        loadTasks()
    }

    private fun loadTasks() {
        repeat(50) {times ->
            val item = TaskItem("Task $times \n \n \n", true)
            taskList.add(item)
        }
    }

    fun getTaskListSize() = taskList.size
    fun getTasks(): List<TaskItem> = taskList

}