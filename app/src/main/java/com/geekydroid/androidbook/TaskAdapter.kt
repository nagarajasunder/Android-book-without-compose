package com.geekydroid.androidbook

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.geekydroid.androidbook.databinding.ItemLayoutBinding

class TaskAdapter(private val taskList: List<TaskItem>) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {


    class ViewHolder(private val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(task:TaskItem) {
            binding.taskName = task.taskName
            binding.taskStatus = task.isCompleted
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_layout,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun getItemCount() = taskList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(taskList[position])
    }

    
}