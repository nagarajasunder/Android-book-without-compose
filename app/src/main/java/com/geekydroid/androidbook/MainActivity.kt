package com.geekydroid.androidbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.geekydroid.androidbook.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private val viewmodel:MainViewmodel by viewModels()
    @Inject
    lateinit var cryptoManager: CryptoManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        setContentView(binding.root)
        binding.model = viewmodel
        binding.lifecycleOwner = this

        binding.btnStore.setOnClickListener {
            viewmodel.storeEncryptedData()
        }
        binding.btnRetrieve.setOnClickListener {
            viewmodel.fetchSecuredData()
        }

    }

}