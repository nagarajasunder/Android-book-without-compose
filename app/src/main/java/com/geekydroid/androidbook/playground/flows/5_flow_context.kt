package com.geekydroid.androidbook.playground.flows

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
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
}.map {
    log("First map $it")
    it
}.flowOn(Dispatchers.Default).map {
        log("Second map $it")
        it
    }.flowOn(Dispatchers.IO)

fun log(msg: String) {
    println("[${Thread.currentThread().name}] $msg")
}