package com.geekydroid.androidbook

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.geekydroid.androidbook.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private const val TAG = "LocationUpdates"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)

        setUI()


    }

    private fun setUI() {


        binding.btnStartFlow.setOnClickListener {
            viewModel.startCollector1()
            viewModel.startCollector2()
        }

        binding.btnObserver1.setOnClickListener {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.intFlowCollector1.collect { randomNum ->
                        binding.tvCollector1.text = randomNum.toString()
                    }
                }
            }
        }

        binding.btnObserver2.setOnClickListener {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.intFlowCollector2.collect { randomNum ->
                        binding.tvCollector2.text = randomNum.toString()
                    }
                }
            }
        }

        binding.btnStopObserver1.setOnClickListener {
            viewModel.stopCollector1()
        }

        binding.btnStopObserver2.setOnClickListener {
            viewModel.stopCollector2()
        }


    }
}