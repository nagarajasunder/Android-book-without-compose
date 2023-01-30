package com.geekydroid.androidbook.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.geekydroid.androidbook.R

class OwnTextView @RequiresApi(Build.VERSION_CODES.Q) constructor(
    context: Context,
    attrs: AttributeSet
) : TextView(context, attrs) {

    private lateinit var backgroundPaint:Paint

    init {
        backgroundPaint = Paint()
        backgroundPaint.color = context.getColor(R.color.purple_200)
        backgroundPaint.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawRect(0f,0f,width.toFloat(),height.toFloat(),backgroundPaint)
        super.onDraw(canvas)
    }

}