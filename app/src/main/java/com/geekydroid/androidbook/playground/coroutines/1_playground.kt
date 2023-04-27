package com.geekydroid.androidbook.playground.coroutines

import kotlinx.coroutines.*

fun main() = runBlocking {

    val customScope = supervisorScope { launch {  } }

    val childExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Child excpetion handler called")
    }

    val parentJob = CoroutineScope(customScope).launch{
        launch(childExceptionHandler) {
           delay(1000)
            throw Exception("Exception thrown by child 1")
       }
        launch {
            println("child 2 started")
            delay(2000)
            println("child 2 completed")
        }
    }
    delay(3000)
    parentJob.join()
    println("Main completed")
}

suspend fun getSharedScope() : Job {
    val job = supervisorScope {  launch {  }}
    return job
}