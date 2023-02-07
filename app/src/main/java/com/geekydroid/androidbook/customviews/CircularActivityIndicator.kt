package com.geekydroid.androidbook.customviews

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Scroller
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.geekydroid.androidbook.R
import kotlin.math.atan2
import kotlin.math.sqrt

private const val TAG = "CircularActivityIndicat"

class CircularActivityIndicator(context: Context, attributeSet: AttributeSet) :
    View(context, attributeSet) {


    private var clipPath: Path? = null
    private var viewPressed = false
    private var lastX: Float = 0.0f
    private var lastY = 0.0f
    private var backgroundBitmap: Bitmap? = BitmapFactory.decodeResource(context.resources, R.drawable.androidsample)
    private lateinit var sourceRect: Rect
    private lateinit var destRect: Rect

    companion object {
        private val PRESSED_FG_COLOR: Int = Color.argb(100, 75, 123, 245)
        private val DEFAULT_FG_COLOR: Int = Color.argb(200, 255, 0, 0)
        private val DEFAULT_BG_COLOR = Color.argb(200, 0, 20, 0)
        private lateinit var foregroundPaint: Paint
        private lateinit var backgroundPaint: Paint
        private var selectedAngle: Float = 0f
        var circleSize = 0
    }

    private lateinit var angleScroller: Scroller
    private lateinit var gestureListener: GestureDetector

    init {
        foregroundPaint = Paint()
        foregroundPaint.style = Paint.Style.FILL
        foregroundPaint.color = DEFAULT_FG_COLOR
        selectedAngle = 270f
        backgroundPaint = Paint()
        backgroundPaint.style = Paint.Style.FILL
        backgroundPaint.color = DEFAULT_BG_COLOR
        angleScroller = Scroller(context, null, true)
        gestureListener = GestureDetector(context, object : GestureDetector.OnGestureListener {

            var processed = false

            override fun onDown(e: MotionEvent): Boolean {
                processed = computeAndSetAngle(e.x, e.y)
                if (processed) {
                    parent.requestDisallowInterceptTouchEvent(true)
                    changePressedState(state = true)
                    postInvalidate()
                }
                return processed
            }

            override fun onShowPress(e: MotionEvent) {

            }

            override fun onSingleTapUp(e: MotionEvent): Boolean {
                endGesture()
                return false
            }

            override fun onScroll(
                e1: MotionEvent,
                e2: MotionEvent,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                computeAndSetAngle(e2.x, e2.y)
                postInvalidate()
                return true
            }

            override fun onLongPress(e: MotionEvent) {
                endGesture()
            }

            override fun onFling(
                e1: MotionEvent,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                return false
            }

        })
        sourceRect = Rect()
        sourceRect.top = 0
        sourceRect.left = 0
        if (backgroundBitmap != null) {
            sourceRect.left = backgroundBitmap!!.width/2
            sourceRect.right = backgroundBitmap!!.width
            sourceRect.bottom = backgroundBitmap!!.height
        }
        destRect = Rect()
    }

    private fun endGesture() {
        parent.requestDisallowInterceptTouchEvent(false)
        changePressedState(false)
        postInvalidate()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (backgroundBitmap != null) {

//            val bitmapHeight = backgroundBitmap!!.height
//            val bitmapWidth = backgroundBitmap!!.width
//
//            var bitmapSize = bitmapWidth
//            if (bitmapWidth < bitmapHeight) {
//                bitmapSize = bitmapHeight
//            }
//
//            val horMargin = (width - bitmapSize)/2
//            val verMargin = (height - bitmapSize)/2
//
//            destRect.top = verMargin
//            destRect.left = horMargin
//            destRect.right = horMargin + bitmapSize
//            destRect.bottom = verMargin + bitmapSize
            configureBitmapDimensions()
            canvas?.drawBitmap(backgroundBitmap!!,sourceRect,destRect,null)
        } else {
            Toast.makeText(context,"Bitmap null",Toast.LENGTH_SHORT).show()
        }

        if (viewPressed) {
            foregroundPaint.color = PRESSED_FG_COLOR
        } else {
            foregroundPaint.color = DEFAULT_FG_COLOR
        }

        circleSize = width
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
        canvas.rotate(-90f, (width / 2).toFloat(), (height / 2).toFloat())

        canvas.drawArc(
            horMargin.toFloat(),
            verMargin.toFloat(),
            (horMargin + circleSize).toFloat(),
            (verMargin + circleSize).toFloat(),
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

    private fun configureBitmapDimensions() {
        val bitmapWidth = backgroundBitmap!!.width
        val bitmapHeight = backgroundBitmap!!.height
        if ((bitmapWidth > bitmapHeight && height > width) ||
            (bitmapWidth <= bitmapHeight && width >= height)
        ) {
            val ratio = height.toFloat()/bitmapHeight
            val scaledWidth = (bitmapWidth * ratio).toInt()
            destRect.top = 0
            destRect.bottom = height
            destRect.left = (width - scaledWidth)/2
            destRect.right = destRect.left + scaledWidth
        } else {
            val ratio = width.toFloat()/bitmapWidth
            val scaledWidth = (bitmapHeight * ratio).toInt()
            destRect.left = 0
            destRect.right = width
            destRect.top = 0
            destRect.bottom = scaledWidth
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        val processed: Boolean
        Log.d(TAG, "onTouchEvent: [x,y] = [${event!!.x},${event.y}]")
        return when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                processed = computeAndSetAngle(event.x, event.y)
                /**
                 * We use this condition in the ACTION_DOWN event because this the first event that gets delivered when the user touches the display
                 */

                if (processed) {
                    parent.requestDisallowInterceptTouchEvent(true)
                    changePressedState(true)
                }
                return processed
            }
            MotionEvent.ACTION_UP -> {
                parent.requestDisallowInterceptTouchEvent(false)
                changePressedState(false)
                true
            }
            MotionEvent.ACTION_MOVE -> {
                processed = computeAndSetAngle(event.x, event.y)
                invalidate()
                return processed
            }
            else -> {
                false
            }
        }
    }

    private fun changePressedState(state: Boolean) {
        viewPressed = state
        invalidate()
    }

    private fun computeAndSetAngle(x: Float, y: Float): Boolean {
        val dx = x - width / 2
        val dy = y - height / 2

        val radius: Double = sqrt(dx * dx + dy * dy).toDouble()
        if (radius > circleSize) return false

        val angle = (180.0 * atan2(dy.toDouble(), dx.toDouble()) / Math.PI) + 90
        Log.d(TAG, "computeAngle: angle $angle")
        selectedAngle = if (angle > 0) angle.toFloat() else (360 + angle).toFloat()


        if (angleScroller.computeScrollOffset()) {
            angleScroller.forceFinished(true)
        }

        angleScroller.startScroll(angleScroller.currX, 0, (angle - angleScroller.currY).toInt(), 0)


        return true
    }

}