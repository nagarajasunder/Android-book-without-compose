package com.geekydroid.androidbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.geekydroid.androidbook.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        binding.btnPaymentScreen.setOnClickListener {
            openPaymentScreen()
        }
    }

    private fun openPaymentScreen() {
        startActivity(Intent(applicationContext,DaasActivity::class.java))
    }
}