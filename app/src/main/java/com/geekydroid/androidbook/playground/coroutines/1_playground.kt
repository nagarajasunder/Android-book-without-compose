@file:OptIn(DelicateCoroutinesApi::class)

package com.geekydroid.androidbook.playground.coroutines

import kotlinx.coroutines.*

fun main() = runBlocking {
    val parentJob = launch {
        launch {
            delay(500)
            println("job 1 completed")
        }
        launch {
            delay(1000)
            println("job 2 completed")
        }
        launch {
            delay(1500)
            println("job 3 completed")
        }
        launch {
            delay(2000)
            println("job 4 completed")
        }
    }
    delay(1200L)
    parentJob.cancel()
    parentJob.join()
    println("main: completed")
}