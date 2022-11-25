package com.geekydroid.androidbook.playground.coroutines

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    println("Main starts")
    joinAll(
        async { coroutine(1, 500) },
        async { coroutine(2, 300) }
    )
    println("Main ends")

}

suspend fun coroutine(number: Int, delayMs: Long) {
    println("coroutine $number starts")
    delay(delayMs)
    println("coroutine $number finished")
}