package com.geekydroid.androidbook.playground.flows

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    simple()
        .conflate()
        .collect {
        delay(500)
        println("Collected Value $it")
    }
}