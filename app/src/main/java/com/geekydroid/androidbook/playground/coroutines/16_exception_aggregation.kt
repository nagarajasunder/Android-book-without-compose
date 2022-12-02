@file:OptIn(DelicateCoroutinesApi::class)

package com.geekydroid.androidbook.playground.coroutines

import kotlinx.coroutines.*
import java.io.FileNotFoundException
import java.io.IOException


fun main() = runBlocking {

    val handler = CoroutineExceptionHandler { _, throwable ->
        println("Exception caught $throwable with suppressed ${throwable.suppressed.contentToString()}")
    }

    val job = GlobalScope.launch(handler) {

        launch {
            try {
                delay(Long.MAX_VALUE)
            }
            finally {
                throw IndexOutOfBoundsException()
            }
        }

        launch {
            try {
                delay(Long.MAX_VALUE)
            }
            finally {
                throw FileNotFoundException()
            }
        }
        launch {
            delay(1000)
            throw IOException()
        }
    }

    job.join()
    println("main completed")

}