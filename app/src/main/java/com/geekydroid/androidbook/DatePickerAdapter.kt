package com.geekydroid.androidbook

import android.app.ActionBar.LayoutParams
import android.app.AlarmManager
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.geekydroid.androidbook.databinding.DateItemLayoutBinding
import org.joda.time.DateTime

class DatePickerAdapter(
    val itemWidth: Int,
    totalDaysToCreate: Int, pastFromDate: Int
) :
    RecyclerView.Adapter<DatePickerAdapter.ViewHolder>() {

    private var days: MutableList<CalendarDay> = mutableListOf()

    inner class ViewHolder(private val binding: DateItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(day: CalendarDay) {
            binding.tvDay.text = day.getFormattedDate("EEE")
            binding.tvDate.text = day.getFormattedDate("dd")
            binding.tvYear.text = day.getFormattedDate("yyyy")
            binding.tvMonth.text = day.getFormattedDate("MMM")
            binding.tvDay.width = itemWidth

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: DateItemLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.date_item_layout,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount() = days.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(days[position])
    }

    init {
        generateDays(
            totalDaysToCreate,
            DateTime().withTime(0, 0, 0, 0).minusDays(pastFromDate).millis
        )
    }

    private fun generateDays(totalDaysToCreate: Int, initialDate: Long) {
        for (i in 0..totalDaysToCreate) {
            days.add(CalendarDay(DateTime(initialDate + INTERVAL_DAY * i).withTime(0, 0, 0, 0)))
        }
    }


    companion object {
        const val INTERVAL_DAY = AlarmManager.INTERVAL_DAY
    }
}