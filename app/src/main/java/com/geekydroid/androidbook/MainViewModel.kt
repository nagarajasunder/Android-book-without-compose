package com.geekydroid.androidbook

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MainViewModel"

@HiltViewModel
class MainViewModel @Inject constructor(
    private val globalRandomGenerator: GlobalRandomGenerator,
    private val externalScope: CoroutineScope
) : ViewModel() {

    private var collectorJob1: Job? = null
    private var collectorJob2: Job? = null


    private val _intFlowCollector1: MutableStateFlow<Int> = MutableStateFlow(0)
    val intFlowCollector1: StateFlow<Int> = _intFlowCollector1

    private val _intFlowCollector2: MutableStateFlow<Int> = MutableStateFlow(0)
    val intFlowCollector2: StateFlow<Int> = _intFlowCollector1


    fun startCollector1() {
        collectorJob1 = externalScope.launch {
            globalRandomGenerator.randomNumFlow.collect {
                _intFlowCollector1.value = it
            }
        }
    }

    fun startCollector2() {
        collectorJob2 = externalScope.launch {
            globalRandomGenerator.randomNumFlow.collect {
                delay(8000)
                _intFlowCollector2.value = it

            }
        }
    }

    fun stopCollector1() {
        collectorJob1?.cancel()
    }

    fun stopCollector2() {
        collectorJob2?.cancel()
    }

}