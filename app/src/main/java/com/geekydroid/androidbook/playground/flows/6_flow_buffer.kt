package com.geekydroid.androidbook.playground.flows

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    val time = measureTimeMillis {
        simple()
            .buffer()
            .collect{ value ->
            delay(700)
            println(value)
        }
    }
    println("Collected in $time ms")
}