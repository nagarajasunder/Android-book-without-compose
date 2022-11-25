package com.geekydroid.androidbook.playground.coroutines

import kotlinx.coroutines.*
import kotlin.concurrent.thread

@OptIn(DelicateCoroutinesApi::class)
fun main() {

    runBlocking {
        repeat(50) { num ->
            launch(context = newSingleThreadContext("My own Thread $num")) {
                println("$num start working on thread  ${Thread.currentThread().name}")
                delay(500)
                println("$num end working on thread ${Thread.currentThread().name}")
            }
        }
    }
}

suspend fun SwitchingCoroutine(number: Int, delayMs: Long) {
    println("coroutine $number starts")
    delay(delayMs)
    println("coroutine $number finished")
}