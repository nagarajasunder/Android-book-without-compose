package com.geekydroid.androidbook

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import org.joda.time.DateTime
import org.joda.time.Days

class HorizontalDatePicker @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr) {

    private var initialDate: DateTime? = null
    private var recyclerView: DatePickerRecyclerView? = null

    fun init(totalCalendarDays: Int, pastFromDate: Int, startDate: DateTime) {
        View.inflate(context, R.layout.horizontal_date_picker_layout, this)
        recyclerView = findViewById(R.id.date_picker_recyclerview)
        initialDate = startDate
        recyclerView!!.init(totalCalendarDays, pastFromDate)
        postFocus(initialDate!!)
    }

    private fun postFocus(initialDate: DateTime) {
        postDelayed({
            recyclerView!!.setDate(initialDate, false)
            recyclerView!!.setDate(initialDate, true)

        }, 100)
    }

    fun setDate(newDateTime: DateTime) {
        post {
            recyclerView!!.setDate(newDateTime, true)
        }
    }
}