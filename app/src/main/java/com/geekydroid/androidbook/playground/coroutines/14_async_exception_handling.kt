package com.geekydroid.androidbook.playground.coroutines

import kotlinx.coroutines.*

@OptIn(DelicateCoroutinesApi::class)
fun main() = runBlocking {

    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Exception caught ${throwable.message}")
    }

    val job = GlobalScope.async(exceptionHandler) {
        println("Throwing exception from async block")
        throw IndexOutOfBoundsException("Async exception")

    }
    try {
        println("main: ${job.await()}")
    } catch (e:Exception) {
        println("Exception caught")
    }
}