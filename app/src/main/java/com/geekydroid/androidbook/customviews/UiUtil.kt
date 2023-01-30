package com.geekydroid.androidbook.customviews

import android.content.Context

object UiUtil {

    fun dpToPixels(dp:Int,context:Context):Int {
        return (dp*context.resources.displayMetrics.density + 0.5).toInt()
    }

    fun pixelsToDp(dp:Int,context:Context) : Int {
        return (dp/context.resources.displayMetrics.density+0.5).toInt()
    }

}