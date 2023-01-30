package com.geekydroid.androidbook

import org.joda.time.DateTime
import java.util.*

data class CalendarDay(val dateTime: DateTime) {

    fun getFormattedDate(format: String): String {
        return dateTime.toString(format, Locale.ENGLISH)
    }


    val isToday:Boolean
        get() = DateTime().withTime(0,0,0,0).millis == dateTime.millis

}