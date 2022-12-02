package com.geekydroid.androidbook.playground.coroutines

import kotlinx.coroutines.*
import kotlin.time.measureTime

@OptIn(DelicateCoroutinesApi::class)
fun main() = runBlocking {
    val handler = CoroutineExceptionHandler { _, exception ->
        println("Exception thrown from child ${exception.message}")
    }
    val parentJob = GlobalScope.launch {
        supervisorScope {
            launch {
                val result = fakeNetworkCall(1)
                println("Result a $result")
            }
            launch(handler) {
                val result = fakeNetworkCall(2)
                println("Result b $result")
            }
            launch {
                val result = fakeNetworkCall(3)
                println("Result c $result")
            }
        }
    }
    parentJob.join()
    println("main completed")
}

suspend fun fakeNetworkCall(delayCt:Int):Int  {
    delay((delayCt*500).toLong())
    if (delayCt == 2) {
        throw ArrayIndexOutOfBoundsException("Exception thrown")
    }
    return delayCt*500
}