package com.geekydroid.androidbook

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewpagerAdapter(fragmentManager: FragmentManager,lifeCycle:Lifecycle) : FragmentStateAdapter(fragmentManager,lifeCycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        when(position) {
            0 -> return Tab1Fragment()
            1 -> return Tab2Fragment()
        }

        return Tab3Fragment()
    }


}