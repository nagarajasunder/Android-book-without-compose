package com.geekydroid.androidbook.playground.flows

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

fun simple(): Flow<Int> = flow {
    println("Flow Started")
   for (i in 1..3) {
       delay(1000)
       emit(i)
   }
}

fun main() = runBlocking {
    simple().collect{
        println(it)
    }
}