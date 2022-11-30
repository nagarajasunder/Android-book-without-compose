package com.geekydroid.androidbook.playground.coroutines

import kotlinx.coroutines.*

@OptIn(DelicateCoroutinesApi::class)
fun main() = runBlocking {
    val childExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("This exception is from child coroutine ${throwable.message}")
    }

    val parentExceptionHandler = CoroutineExceptionHandler{ _, exception ->
        println("Parent exception handler")
    }
    val job = GlobalScope.launch(parentExceptionHandler) {
        delay(1000)
        throw IndexOutOfBoundsException("This exception is from child")
    }
    joinAll(job)
    println("main: completed")
}