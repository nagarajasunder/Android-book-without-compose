package com.geekydroid.androidbook.customviews

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.geekydroid.androidbook.R

class PointsDrawer : View {


    private val POINTS = 20
   private var path: Path? = null
    private lateinit var strokePaint: Paint

    private lateinit var background:Bitmap
    private lateinit var backgroundTransformation:Matrix

    private var touching = false

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        strokePaint = Paint()
        touching = false

        background = BitmapFactory.decodeResource(resources,R.drawable.background)
        backgroundTransformation = Matrix()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (path == null) {
            val points = FloatArray(POINTS * 3)
            for (i in 0 until POINTS) {
                points[i * 3] = (Math.random() * width).toFloat()
                points[i * 3 + 1] = (Math.random() * height).toFloat()
                points[i * 3 + 2] = (Math.random() * (width / 4f)).toFloat()

            }

            path = Path()

            for (i in 0 until points.size / 3) {
                path!!.addCircle(
                    points[i * 3],
                    points[i * 3 + 1],
                    points[i*3+2],
                    Path.Direction.CW
                )
            }
            path!!.close()


        }

        canvas?.save()

        if (!touching) canvas?.clipPath(path!!)

        if (background != null) {
            backgroundTransformation.reset()
            val scale = width/background.width.toFloat()
            backgroundTransformation.postScale(scale,scale)
            canvas!!.drawBitmap(background,backgroundTransformation,null)
        }
        canvas!!.restore()


//        strokePaint.color = Color.argb(200, 234, 45, 20)
//        strokePaint.style = Paint.Style.FILL
//        strokePaint.isAntiAlias = true
//        canvas?.drawPath(path!!,strokePaint)



//        strokePaint.strokeWidth = 4f
//        strokePaint.strokeCap = Paint.Cap.BUTT
//        canvas?.drawLines(points!!,strokePaint)

//        strokePaint.color = Color.argb(200,20,45,234)
//        strokePaint.strokeWidth = 20f
//        strokePaint.strokeCap = Paint.Cap.ROUND
//        canvas?.drawPoints(points!!,strokePaint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            touching = true
            postInvalidate()
            return true
        }
        if (event?.action == MotionEvent.ACTION_UP) {
            touching = false
            postInvalidate()
            return true
        }

        return false
    }


}