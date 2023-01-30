package com.geekydroid.androidbook.customviews

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View

private const val TAG = "CircularActivityIndicat"

class CircularActivityIndicator : View {

    private var clipPath: Path? = null

    companion object {
        private val DEFAULT_FG_COLOR: Int = Color.argb(200, 255, 0, 0)
        private val DEFAULT_BG_COLOR = Color.argb(200,12,12,12)
        private lateinit var foregroundPaint: Paint
        private lateinit var backgroundPaint:Paint
        private var selectedAngle: Int = 0
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {

        foregroundPaint = Paint()
        foregroundPaint.style = Paint.Style.FILL
        foregroundPaint.color = DEFAULT_FG_COLOR
        selectedAngle = 270

        backgroundPaint = Paint()
        backgroundPaint.style = Paint.Style.FILL
        backgroundPaint.color = DEFAULT_BG_COLOR
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        var circleSize = width
        Log.d(TAG, "onDraw: width $width height $height circle Size $circleSize")
        if (height < circleSize) {
            circleSize = height
        }
        val horMargin = (width - circleSize) / 2
        val verMargin = (height - circleSize) / 2


        if (clipPath == null) {
            val clipWidth = (circleSize * 0.75f).toInt()
            val clipX = (width - clipWidth) / 2
            val clipY = (height - clipWidth) / 2
            clipPath = Path()
            clipPath!!.addArc(
                clipX.toFloat(),
                clipY.toFloat(),
                (clipX + clipWidth).toFloat(),
                (clipY + clipWidth).toFloat(),
                0f,
                360f
            )
        }
        canvas!!.clipRect(0, 0, width, height)
        canvas.clipPath(clipPath!!, Region.Op.DIFFERENCE)

        canvas.save()
        canvas.rotate(-90f, (width/2).toFloat(), (height/2).toFloat())

        canvas.drawArc(
            horMargin.toFloat(),
            verMargin.toFloat(),
            (horMargin+circleSize).toFloat(),
            (verMargin+circleSize).toFloat(),
            0f,
            360f,
            true,
            backgroundPaint
        )

        canvas.drawArc(
            horMargin.toFloat(),
            verMargin.toFloat(),
            (horMargin + circleSize).toFloat(),
            (verMargin + circleSize).toFloat(),
            0f,
            selectedAngle.toFloat(),
            true,
            foregroundPaint
        )

        canvas.restore()
    }

}