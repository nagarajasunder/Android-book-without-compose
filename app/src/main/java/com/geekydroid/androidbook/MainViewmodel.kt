package com.geekydroid.androidbook

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewmodel @Inject constructor() : ViewModel() {

    val valueToShow:MutableLiveData<String> = MutableLiveData("")
    var valueToStore:MutableLiveData<String> = MutableLiveData("")

    fun updateValue(value: String) {
        valueToShow.value = value
    }
}