package com.geekydroid.androidbook.playground.flows

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    flowFun().collect {
        log("Received $it")
    }
}

suspend fun flowFun(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(1000)
        log("Emitting $i")
        emit(i)
    }
}.flowOn(Dispatchers.IO)

fun log(msg:String) {
    println("[${Thread.currentThread().name}] $msg")
}