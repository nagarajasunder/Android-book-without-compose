package com.geekydroid.androidbook.playground.coroutines

import kotlinx.coroutines.*

fun main() = runBlocking {
    val job = launch {
        try {
            repeat(1000) { i ->
                println("job:I'm sleeping at $i")
                delay(500L)
            }
        }
        finally {
           withContext(NonCancellable) {
               println("job:I'm inside finally block")
               delay(1000L)
               println("job: I have just delay 1sec from finally block")
           }
        }
    }
    delay(2000L)
    println("main:I'm tired of waiting")
    job.cancelAndJoin()
    println("main:Now I quit")
}
