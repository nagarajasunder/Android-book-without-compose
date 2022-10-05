package com.geekydroid.androidbook

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewmodel @Inject constructor(private val cryptoManager: CryptoManager) : ViewModel() {

    val valueToShow:MutableLiveData<String> = MutableLiveData("")
    var valueToStore:MutableLiveData<String> = MutableLiveData("")

    fun updateValue(value: String) {
        valueToShow.value = value
    }

    fun storeEncryptedData() {
        viewModelScope.launch {
            val value = valueToStore.value
            if (!value.isNullOrEmpty())
            {
                cryptoManager.storeSecuredData(value)
            }
        }
    }

    fun fetchSecuredData()
    {
        viewModelScope.launch {
            cryptoManager.secureFetch().collect{
                valueToShow.value = it
            }
        }
    }
}