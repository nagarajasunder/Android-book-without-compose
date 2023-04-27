package com.geekydroid.androidbook

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "MainViewModel"

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    fun startWork() {
        viewModelScope.launch {
            repository.parallelWork()
        }
    }


}

@Singleton
class MainRepository @Inject constructor(
    @ApplicationScope private val externalScope: CoroutineScope,
    @IoDispatcher private val externalDispatcher: CoroutineDispatcher
) {

    suspend fun parallelWork() {
        externalScope.launch(externalDispatcher) {
            println("Scope: ${this.coroutineContext[Job.Key]}")
            launch {
                val resultA = jobA()
                println(resultA)
            }
            launch {
                val resultB = JobB()
                println(resultB)
            }
        }
    }

    suspend fun jobA(): String {
        delay(1500)
        return "Job A Completed"
    }

    suspend fun JobB(): String {
        delay(500)
        throw AssertionError("Job B Failed by exception")
    }

}
