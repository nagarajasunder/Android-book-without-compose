package com.geekydroid.androidbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.geekydroid.androidbook.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        setContentView(binding.root)

       setTabLayout()

    }

    private fun setTabLayout() {

        val nameList = listOf("Tab 1","Tab 2","Tab 3")
        val adapter = ViewpagerAdapter(supportFragmentManager,lifecycle)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout,binding.viewPager){ tab, position ->
            tab.text = nameList[position]
        }.attach()

//        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                Toast.makeText(this@MainActivity,"Tab selected position ${tab?.position}",Toast.LENGTH_SHORT).show()
//                when(tab?.id) {
//                    R.id.tab_1 -> Toast.makeText(this@MainActivity,"Tab 1 selected",Toast.LENGTH_SHORT).show()
//                    R.id.tab_2 -> Toast.makeText(this@MainActivity,"Tab 2 selected",Toast.LENGTH_SHORT).show()
//                    R.id.tab_3 -> Toast.makeText(this@MainActivity,"Tab 3 selected",Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//
//            }
//
//        })
    }
}