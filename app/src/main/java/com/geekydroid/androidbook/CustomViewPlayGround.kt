package com.geekydroid.androidbook

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class CustomViewPlayGround : View {

    private val pathPaint = Paint().apply {
        isAntiAlias = true
        color = Color.YELLOW
        strokeWidth = 20f
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }

    private val newPath = Path()

    private fun init() {
        newPath.lineTo(50F,878f)
        newPath.lineTo(266f,342f)
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context:Context,attrs:AttributeSet) : super(context,attrs) {
        init()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawPath(newPath,pathPaint)


    }

}