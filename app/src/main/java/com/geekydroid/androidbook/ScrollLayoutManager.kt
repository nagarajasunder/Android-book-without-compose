package com.geekydroid.androidbook

import android.content.Context
import android.graphics.Color
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs
import kotlin.math.sqrt

class ScrollLayoutManager(private val context: Context, orientation: Int, reverseLayout: Boolean) :
    LinearLayoutManager(
        context, orientation, reverseLayout
    ) {

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        super.onLayoutChildren(recycler, state)
        scaleDownView()
    }

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        scaleDownView()
        return super.scrollHorizontallyBy(dx, recycler, state)
    }

    private fun scaleDownView() {
        val mid = width / 2
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child != null) {
                val childMid = (getDecoratedLeft(child) + getDecoratedRight(child)) / 2
                val distanceFromCenter = abs(mid - childMid)
                var scale = (1 - sqrt((distanceFromCenter / width).toDouble()) * 0.66f).toFloat()
                if (scale < 0.75f) {
                    scale = 0.75f
                }
                child.scaleX = scale
                child.scaleY = scale
                //child.setBackgroundColor(context.resources.getColor(R.color.purple_200))
            }
        }
    }
}