package com.geekydroid.androidbook.playground.coroutines

import kotlinx.coroutines.*
import kotlin.concurrent.thread

fun main() {
    runBlocking {
        repeat(1_000_000) {
            launch {
                delay(1000)
                print("*")
            }
        }
    }
}

