package com.geekydroid.androidbook

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlin.system.measureTimeMillis
import kotlin.time.Duration.Companion.seconds

sealed class CounterMsg
object IncCounter : CounterMsg()
class GetCounter(val response : CompletableDeferred<Int>) : CounterMsg()

@OptIn(ObsoleteCoroutinesApi::class)
fun CoroutineScope.counterActor() = actor<CounterMsg> {
    var counter = 0
    for (msg in channel) {
        when(msg) {
            is GetCounter -> msg.response.complete(counter)
            IncCounter -> counter++
        }
    }
}




fun main() = runBlocking<Unit> {
    val actor = counterActor()
    withContext(Dispatchers.Default) {
        massiveRun {
            // protect each increment with lock
           actor.send(IncCounter)
        }
    }
    val response = CompletableDeferred<Int>()
    actor.send(GetCounter(response))
    println("Counter = ${response.await()}")
    actor.close()
}



suspend fun massiveRun(action : suspend () -> Unit) {

    val n = 100
    val k = 1000

    val time = measureTimeMillis {
        coroutineScope {
            repeat(n) {
                launch {
                    repeat(k) {
                        action()
                    }
                }
            }
        }
    }

    println("Completed ${n*k} operations in ${time}ms")

}
