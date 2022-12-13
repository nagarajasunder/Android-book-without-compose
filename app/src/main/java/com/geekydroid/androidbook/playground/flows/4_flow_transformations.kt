package com.geekydroid.androidbook.playground.flows

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main()  = runBlocking {
    (1..3)
        .asFlow()
        .map {
            performNetworkRequest(it)
        }
        .collect { response ->
            println(response)
        }
}

suspend fun performNetworkRequest(request:Int) : String {
    delay(1000)
    return "Response $request"
}