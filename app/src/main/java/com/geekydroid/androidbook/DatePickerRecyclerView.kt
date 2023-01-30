package com.geekydroid.androidbook

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import org.joda.time.DateTime
import org.joda.time.Days

private const val TAG = "DatePickerRecyclerView"
class DatePickerRecyclerView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context,attributeSet,defStyleAttr) {

    private var adapter: DatePickerAdapter? = null
    private var pastFromDate:Int = 0
    private var smoothScroller:SmoothScroller? = null
    private var layoutManager:LinearLayoutManager? = null
    private var lastPosition = 0

    val scrollListener = object : OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            when(newState) {
                SCROLL_STATE_IDLE -> {
                    val childPosition = this@DatePickerRecyclerView.layoutManager!!.findFirstCompletelyVisibleItemPosition() + (NUMBER_OF_VISIBLE_ITEMS/2)
                    if (childPosition != -1 && childPosition != lastPosition) {
                        smoothScrollToPosition(childPosition)
                    }
                }
            }
        }
    }

    fun init(totalCalendarDays: Int, pastFromDate: Int) {
        this.layoutManager = LinearLayoutManager(context,HORIZONTAL,false)
        setLayoutManager(layoutManager)
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(this@DatePickerRecyclerView)
        this.pastFromDate = pastFromDate
        post {
            val itemWidth = width/ NUMBER_OF_VISIBLE_ITEMS.toFloat()
            adapter = DatePickerAdapter(itemWidth.toInt(),totalCalendarDays,pastFromDate)
            setAdapter(adapter)
        }

    }

    fun setDate(newDateTime: DateTime,smoothScroll:Boolean) {
        val today = DateTime().withTime(0,0,0,0)
        val difference = Days.daysBetween(newDateTime,today).days * if (newDateTime.year < today.millis) -1 else 1
        Log.d(TAG, "setDate: difference $difference")
        if (smoothScroll) {
            smoothScrollToPosition(pastFromDate+difference)
        }
        else {
            layoutManager!!.scrollToPosition(pastFromDate+difference)
        }
    }

    override fun smoothScrollToPosition(position: Int) {
        val scroller = CenterScroller(context)
        scroller.targetPosition = position
        post {
            layoutManager!!.startSmoothScroll(scroller)
        }
    }


    companion object {
        const val NUMBER_OF_VISIBLE_ITEMS = 5
    }

}