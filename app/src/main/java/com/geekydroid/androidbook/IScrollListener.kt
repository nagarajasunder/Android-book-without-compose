package com.geekydroid.androidbook

interface IScrollListener {

    fun onScrollStopped()
    fun onScroll()
    fun onDateSelected(item:CalendarItem?)
}