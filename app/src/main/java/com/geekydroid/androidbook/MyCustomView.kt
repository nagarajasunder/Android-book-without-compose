package com.geekydroid.androidbook

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

private const val TAG = "MyCustomView"
class MyCustomView : View {

    private var stepsCirclePaintList = mutableListOf<Paint>()
    private var currentStep = 0
    private var lineLength:Float? = null
    private val linePathList = mutableListOf<Path>()
    private val EXPAND_MARK = 1.3F
    private lateinit var indicators:FloatArray
    private val circleStrokePaint = Paint().apply {
        isAntiAlias = true
        color = Color.RED
        style = Paint.Style.STROKE
        strokeWidth = 4f
    }
    private val linePaint = Paint().apply {
        isAntiAlias = true
        color = Color.BLUE
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 5f
    }
    private val circleFillPaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.BLUE
    }
    private val circleCount = 3
    private val circleRadius = dpToPx(context,20f)
    private val lineMargin = resources.getDimension(R.dimen.stpi_default_line_margin);

    constructor(context:Context) : super(context) {
        init()
    }

    private fun init() {
        compute()

    }

    constructor(context:Context,attrs:AttributeSet) : super(context,attrs) {
        init()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val minW = paddingLeft + paddingRight + suggestedMinimumWidth
        val w = resolveSizeAndState(minW,widthMeasureSpec,1)

        val minH = MeasureSpec.getSize(w) + paddingTop + paddingBottom
        val h = resolveSizeAndState(minH,heightMeasureSpec,0)

        setMeasuredDimension(w,h)
   }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val y = getStepCenterY()
        for (i in indicators.indices) {
            val indicator = indicators[i]
            canvas?.drawCircle(indicator,y,circleRadius,getStepCirclePaint(i))
            if (i != indicator.size - 1) {
                canvas?.drawPath(linePathList[i],linePaint)
            }
        }

    }

    private fun getStepCirclePaint(index: Int): Paint {

    }

    private fun getCenterY(): Int {
        return measuredHeight/2
    }

    private fun compute() {
        indicators = FloatArray(circleCount)
        val startX = circleRadius * EXPAND_MARK + circleStrokePaint.strokeWidth/2f
        val divider = (measuredWidth - startX*2f)/(circleCount-1)
        val lineLength = divider - (circleRadius*2f+circleStrokePaint.strokeWidth) - (lineMargin*2)
        for (i in indicators.indices) {
            indicators[i] = startX + divider * i
        }

        for (i in 0 until indicators.size - 1) {
            val position = (indicators[i]+indicators[i+1])/2
            val linePath = Path()
            val lineY = getStepCenterY()
            linePath.moveTo(position,lineY)
            linePath.lineTo(position+lineLength,lineY)
            linePathList.add(linePath)
        }
        invalidate()
    }

    private fun getStepCenterY(): Float {
        return measuredHeight/2f
    }

    private fun dpToPx(context: Context, dp: Float): Float {
        return dp * context.resources.displayMetrics.density
    }
}