package com.geekydroid.androidbook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.geekydroid.androidbook.databinding.FragmentTab1Binding
import com.geekydroid.androidbook.databinding.FragmentTab3Binding


class Tab3Fragment : Fragment() {

    private lateinit var binding:FragmentTab3Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_tab3,
            container,
            false
        )
        // Inflate the layout for this fragment
        return binding.root
    }


}