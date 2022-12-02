package com.geekydroid.androidbook.playground.coroutines

import kotlinx.coroutines.*

fun main() = runBlocking {
    val startTime = System.currentTimeMillis()
    val parentJob = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0
        while (i < 5) {
            if (isActive) {
                if (System.currentTimeMillis() >= nextPrintTime) {
                    println("job: I'm sleeping ${i++}")
                    nextPrintTime += 500L
                }
            } else {
                break
            }
        }
    }
    delay(1200L)
    println("main:I'm tired of waiting")
    parentJob.cancelAndJoin()
    println("main:Now I quit")
}

