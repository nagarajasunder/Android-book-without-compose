package com.geekydroid.androidbook.playground.flows

import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    println("Calling Flow")
    val flow = simple()
    println("Calling collect")
    flow.collect{
        println(it)
    }
}