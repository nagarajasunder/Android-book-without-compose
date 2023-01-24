package com.geekydroid.androidbook.playground.flows

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    combineExample()

}

suspend fun zipExample() {
    val flow1 = flowOf("A", "B", "C").onEach { delay(300) }
    val flow2 = flowOf("1", "2", "3").onEach { delay(400) }
    flow1.zip(flow2) { a, b -> "Emitted Value $a $b" }
        .collect {
            println(it)
        }
}

suspend fun combineExample() {
    val flow1 = flowOf("A", "B", "C").onEach { delay(300) }
    val flow2 = flowOf("1", "2", "3").onEach { delay(400) }
    flow1.combine(flow2) { a, b -> "Emitted Value $a $b" }
        .collect {
            println(it)
        }
}