package com.geekydroid.androidbook


import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.system.measureTimeMillis


private const val TAG = "GlobalRandomGenerator"

@Singleton
class GlobalRandomGenerator @Inject constructor(
    private val externalScope: CoroutineScope
) {

    private val _intMutableSharedFlow: MutableSharedFlow<Int> = MutableSharedFlow(
        replay = 1,
        extraBufferCapacity = 0,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val randomNumFlow: SharedFlow<Int> = _intMutableSharedFlow

    private var lastEmissionTime = 0L

    init {
        externalScope.launch {
            while (true) {
                val randomNum = randomGenerator()
                lastEmissionTime = measureTimeMillis {
                    _intMutableSharedFlow.emit(randomNum)
                    delay(1000)
                }
                Log.d(
                    TAG,
                    "Emitted in : $randomNum subs ${_intMutableSharedFlow.subscriptionCount.value} ${lastEmissionTime}"
                )
            }
        }
    }


    private fun randomGenerator(): Int {
        return kotlin.random.Random.nextInt(1000)
    }
}