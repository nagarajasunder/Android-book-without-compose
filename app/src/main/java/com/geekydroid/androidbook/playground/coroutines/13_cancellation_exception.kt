package com.geekydroid.androidbook.playground.coroutines

import kotlinx.coroutines.*

fun main() = runBlocking {

    val parentJob = launch {

        val childJob = launch {
            try {
                delay(Long.MAX_VALUE)
            }
            finally {
               println("child is cancelled")
            }
        }

        yield()
        println("Cancelling child")
        childJob.cancel()
        childJob.join()
        yield()
        println("Parent job is not cancelled")
    }

    parentJob.join()

}