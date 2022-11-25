package com.geekydroid.androidbook.playground.coroutines

import kotlinx.coroutines.*
import kotlin.concurrent.thread

fun main() {
    repeat(1_000_000) {
        thread {
            Thread.sleep(5000)
            print("*")
        }
    }
}

