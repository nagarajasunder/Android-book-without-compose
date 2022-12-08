package com.geekydroid.androidbook

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.geekydroid.androidbook.databinding.DateItemLayoutBinding
import java.util.Calendar

class DateSelectorAdapter(
    private val items: List<CalendarItem>
) : RecyclerView.Adapter<DateSelectorAdapter.DateSelectorViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateSelectorViewHolder {
        val binding: DateItemLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.date_item_layout,
            parent,
            false
        )

        return DateSelectorViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: DateSelectorViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun getItem(position: Int): CalendarItem {
        return if (position < items.size) items[position] else items[items.size - 1]
    }

    class DateSelectorViewHolder(private val binding: DateItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var selectedTextColor = Color.BLACK
        private var unSelectedTextColor = Color.LTGRAY

        fun bind(item: CalendarItem) {
            binding.data = item.text
            if (item.isSelected) {
                binding.tvItemText.setTextColor(selectedTextColor)
            } else {
                binding.tvItemText.setTextColor(unSelectedTextColor)
            }
            binding.executePendingBindings()
        }
    }
}