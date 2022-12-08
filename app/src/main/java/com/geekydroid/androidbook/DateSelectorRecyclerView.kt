package com.geekydroid.androidbook

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

class DateSelectorRecyclerView @JvmOverloads constructor(context:Context,attr:AttributeSet? = null,defaultStyleAttr:Int = 0) : RecyclerView(
    context,attr,defaultStyleAttr
) {

    private var mItemList:List<CalendarItem>? = null
    private var adapter:DateSelectorAdapter? = null
    private var mLayoutManager:LinearLayoutManager? = null
    private var listener:IScrollListener? = null
    private var lastSelectedPosition = 0


    fun init(itemList: List<CalendarItem>) {
        mLayoutManager = ScrollLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        mItemList = itemList
        adapter = DateSelectorAdapter(mItemList!!)
        layoutManager = mLayoutManager
        addOnScrollListener(mScrollListener)
        setAdapter(adapter)
    }

    private var mScrollListener = object : OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            when(newState) {
                SCROLL_STATE_IDLE -> {
                    listener?.onScrollStopped()
                    val childPosition = mLayoutManager!!.findFirstCompletelyVisibleItemPosition() + (NUMBER_OF_VISIBLE_ITEMS/2)
                    if (childPosition != -1 && childPosition != lastSelectedPosition) {
                        setIsSelected(true,childPosition)
                        setIsSelected(false,lastSelectedPosition)
                        lastSelectedPosition = childPosition
                    }
                }
            }
        }
    }

    fun setSelection(selectedItem:Int) {
        val diff = abs(selectedItem - ((-20+9)/2))
        layoutManager!!.scrollToPosition(diff)
    }

    private fun setIsSelected(isSelected:Boolean,position:Int) {
        adapter!!.getItem(position).isSelected = isSelected
        adapter!!.notifyItemChanged(position)
    }

    fun setListener(listener:IScrollListener) {
        this.listener = listener
    }

    fun getCurrentSelectedItemPosition() = lastSelectedPosition

    companion object {
        private const val NUMBER_OF_VISIBLE_ITEMS = 5
    }
}