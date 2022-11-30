@file:OptIn(DelicateCoroutinesApi::class)

package com.geekydroid.androidbook.playground.coroutines

import kotlinx.coroutines.*

fun main() = runBlocking {

    val excpetionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Exception caught ${throwable.message}")
    }

    val parentJOb = GlobalScope.launch(excpetionHandler) {

        launch {
            try {
                delay(Long.MAX_VALUE)
            }
            finally {
                withContext(NonCancellable) {
                    println("The job has cancelled but I'm in finally block so parent should wait until I complete")
                    delay(1000)
                    println("finally block completed")
                }
            }
        }

        launch {
            throw IndexOutOfBoundsException("Child 2 thrown exception")
        }

    }

    parentJOb.join()
    println("main: completed")

}