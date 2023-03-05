package com.geekydroid.androidbook.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View

private const val TAG = "PrimitiveDrawer"

class PrimitiveDrawer : View {

    private val rects = arrayListOf<Float>()
    private val colors = arrayListOf<Int>()
    private var paint: Paint = Paint()

    constructor(context: Context, attr: AttributeSet) : super(context, attr) {
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true
    }

    var temp = true


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        for (i in 0 until 2) {
            rects.add((Math.random() * width).toFloat())
            rects.add((Math.random() * height).toFloat())
        }

        if (temp) {
//            colors.add(-0x1000000 or (0xffffff * Math.random()).toInt())
            colors.add(44534576)
            temp = !temp
        } else {
            colors.add(276)
            temp = !temp
        }

        for (i in 0 until rects.size / 4) {
            paint.color = colors[i]
            Log.d(TAG, "onDraw: ${colors[i]}")
            canvas?.drawRoundRect(
                rects[i * 4],
                rects[i * 4 + 1],
                rects[i * 4 + 2],
                rects[i * 4 + 3],
                40f, 40f, paint
            )
        }

        if (rects.size < 100) {
            postInvalidateDelayed(20)
        }
    }
}