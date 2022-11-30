@file:OptIn(DelicateCoroutinesApi::class)

package com.geekydroid.androidbook.playground.coroutines

import kotlinx.coroutines.*

fun main() = runBlocking {
    GlobalScope.launch {
        delay(3000)
        println("lauch completed")
    }

    delay(2000)
    println("main completed")
}