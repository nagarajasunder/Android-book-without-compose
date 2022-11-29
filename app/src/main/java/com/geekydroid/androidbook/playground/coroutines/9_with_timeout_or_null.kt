package com.geekydroid.androidbook.playground.coroutines

import kotlinx.coroutines.*

fun main() = runBlocking {
    withTimeout(1300L) {
        repeat(1000) { i ->
            println("job:I'm sleeping at $i")
            delay(500L)
        }
        println("Done")
    }
//    val result = withTimeoutOrNull(1300L) {
//        repeat(1000) { i ->
//            println("job:I'm sleeping at $i")
//            delay(500L)
//        }
//        println("Done")
//    }
//    println("Result is $result")
}