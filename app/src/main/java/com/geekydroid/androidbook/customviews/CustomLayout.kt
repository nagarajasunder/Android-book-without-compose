package com.geekydroid.androidbook.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

class CustomLayout(context: Context?, attrs: AttributeSet?) :
    ViewGroup(context, attrs) {
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val count = childCount
        var left = l + paddingLeft
        var top = t + paddingTop

        // keeps track of maximum row height
        var rowHeight = 0
        for (i in 0 until count) {
            val child: View = getChildAt(i)
            val childWidth: Int = child.getMeasuredWidth()
            val childHeight: Int = child.getMeasuredHeight()

            // if child fits in this row put it there
            if (left + childWidth < r - paddingRight) {
                child.layout(left, top, left + childWidth, top + childHeight)
                left += childWidth
            } else {
                // otherwise put it on next row
                left = l + paddingLeft
                top += rowHeight
                rowHeight = 0
                child.layout(left, top, left + childWidth, top + childHeight)
                left += childWidth
            }

            // update maximum row height
            if (childHeight > rowHeight) rowHeight = childHeight
        }
    }

    private fun getMeasure(spec: Int, desired: Int): Int {
        return when (MeasureSpec.getMode(spec)) {
            MeasureSpec.EXACTLY -> MeasureSpec.getSize(spec)
            MeasureSpec.AT_MOST -> Math.min(MeasureSpec.getSize(spec), desired)
            MeasureSpec.UNSPECIFIED -> desired
            else -> desired
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (width == 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            return
        }
        val count = childCount
        var rowHeight = 0
        var maxWidth = 0
        var maxHeight = 0
        var left = 0
        var top = 0
        for (i in 0 until count) {
            val child: View = getChildAt(i)
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            val childWidth: Int = child.getMeasuredWidth()
            val childHeight: Int = child.getMeasuredHeight()

            // if child fits in this row put it there
            if (left + childWidth < width) {
                left += childWidth
            } else {
                // otherwise put it on next row
                if (left > maxWidth) maxWidth = left
                left = 0
                top += rowHeight
                rowHeight = 0
            }

            // update maximum row height
            if (childHeight > rowHeight) rowHeight = childHeight
        }
        if (left > maxWidth) maxWidth = left
        //The below statement is to calculate the row height for the last row(effectively for the whole view also)
        maxHeight = top + rowHeight
        setMeasuredDimension(
            getMeasure(widthMeasureSpec, maxWidth),
            getMeasure(heightMeasureSpec, maxHeight)
        )
    }
}