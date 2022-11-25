package com.geekydroid.androidbook.playground.coroutines

import kotlinx.coroutines.*
import kotlin.concurrent.thread

@OptIn(DelicateCoroutinesApi::class)
fun main() {

    runBlocking {
        val parentJob = launch {

            launch {
                println("Job 1 started")
                delay(2000)
                println("Job 1 completed")
            }

            launch(Job()) {
                println("Job 2 started")
                delay(5000)
                println("Job 2 completed")
            }


//           launch(Job()) {
//                println("I'm job 1 and I run independently")
//                delay(1000)
//                println("Job 1 completed")
//            }
//
//            launch {
//                println("I'm job 2 and I'm a children on parent job")
//                delay(2000)
//                println("Job 2 completed")
//            }.join()
//
//            println("Job 3 started")
//            GlobalScope.launch {
//                println("I'm job 3 and I run independently")
//                delay(1000)
//                println("Job 3 completed")
//            }
        }
        delay(500)
        //parentJob.cancel()
        //delay(6000)
    }
}

