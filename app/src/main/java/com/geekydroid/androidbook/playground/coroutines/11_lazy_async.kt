package com.geekydroid.androidbook.playground.coroutines

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() = runBlocking(CoroutineName("runblockingcr")) {
    val time = measureTimeMillis {
        val one = async(start = CoroutineStart.LAZY , context = CoroutineName("async1")) {
            fakeNetworkCall1()
        }
        val two = async(start = CoroutineStart.LAZY, context = CoroutineName("async2")) {
            fakeNetworkCall2()
        }
        one.start()
        two.start()
        println("The result is ${one.await()+two.await()}")
    }
    println("Completed in $time Ms")
}