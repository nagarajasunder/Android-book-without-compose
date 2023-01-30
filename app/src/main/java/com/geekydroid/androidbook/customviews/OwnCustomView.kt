package com.geekydroid.androidbook.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class OwnCustomView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null) :
    View(context, attrs) {
    private val backgroundPaint: Paint

    init {
        backgroundPaint = Paint()
        backgroundPaint.style = Paint.Style.FILL
        backgroundPaint.color = DEFAULT_FILL_COLOR
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = getMeasurementSize(widthMeasureSpec, DEFAULT_SIZE)
        val height = getMeasurementSize(heightMeasureSpec, DEFAULT_SIZE)
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        val leftX = paddingLeft
        val rightX = width - paddingLeft - paddingRight
        val topY = paddingTop
        val bottomY = height - paddingTop - paddingBottom
        canvas.drawRect(
            leftX.toFloat(),
            topY.toFloat(),
            rightX.toFloat(),
            bottomY.toFloat(),
            backgroundPaint
        )
        super.onDraw(canvas)
    }

    companion object {
        private val TAG = OwnCustomView::class.java.name
        private const val DEFAULT_SIZE = 2000
        private const val DEFAULT_FILL_COLOR = -0x10000
        private fun getMeasurementSize(measureSpec: Int, defaultSize: Int): Int {
            val mode = MeasureSpec.getMode(measureSpec)
            val size = MeasureSpec.getSize(measureSpec)
            return when (mode) {
                MeasureSpec.EXACTLY -> size
                MeasureSpec.AT_MOST -> Math.min(defaultSize, size)
                MeasureSpec.UNSPECIFIED -> defaultSize
                else -> defaultSize
            }
        }
    }
}