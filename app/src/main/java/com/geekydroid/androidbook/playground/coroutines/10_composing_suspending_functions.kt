package com.geekydroid.androidbook.playground.coroutines

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    val time = measureTimeMillis {
        val one = async { fakeNetworkCall1() }
        val two = async { fakeNetworkCall2() }
        println("The answer is ${one.await()+two.await()}")
    }
    println("Completed in $time ms")
}

suspend fun fakeNetworkCall1():Int {
    delay(1000L)
    return 25
}

suspend fun fakeNetworkCall2():Int {
    delay(1000L)
    return 88
}