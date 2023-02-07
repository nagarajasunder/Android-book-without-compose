package com.geekydroid.androidbook.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.geekydroid.androidbook.R

class CustomViewPlayground(context: Context, attr: AttributeSet) : View(
    context,
    attr
) {

    private val backgrountPaint = Paint()

    init {
        backgrountPaint.color = 0xffff4500.toInt()
        backgrountPaint.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        var circleSize = height
        if (width < height) {
            circleSize = width
        }

        val horMargin = (width - circleSize)/2
        val verticalMargin = (height - circleSize)/2

        canvas?.drawArc(
            horMargin.toFloat(),
            verticalMargin.toFloat(),
            horMargin + circleSize.toFloat(),
            verticalMargin + circleSize.toFloat(),
            0F,
            360f,
            true,
            backgrountPaint
        )

    }
}