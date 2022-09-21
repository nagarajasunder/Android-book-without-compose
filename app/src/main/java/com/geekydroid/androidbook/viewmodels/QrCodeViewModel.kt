package com.geekydroid.androidbook.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekydroid.androidbook.UpiStatusResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QrCodeViewModel @Inject constructor() : ViewModel() {

    private var _upiPaymentResponse:MutableLiveData<UpiStatusResponse?> = MutableLiveData()
    var upiPaymentResponse:LiveData<UpiStatusResponse?> = _upiPaymentResponse
    private var _paymentStatus:MutableLiveData<String> = MutableLiveData("Not Yet Started")
    var paymentStatus:LiveData<String> = _paymentStatus

    fun getUpiPaymentStatus() {
        viewModelScope.launch {
            _paymentStatus.postValue("Started")
            delay(1000L)
            _paymentStatus.postValue("Inprogress")
            delay(5000L)
            val response = UpiStatusResponse()
            response.status = "Success"
            response.amountPaid = 390
            _upiPaymentResponse.postValue(response)
            _paymentStatus.postValue("Completed")
        }
    }
}