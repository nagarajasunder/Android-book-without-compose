package com.geekydroid.androidbook

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor() : ViewModel() {

    var paymentAmount:MutableLiveData<String> = MutableLiveData("")
    var upiDiscountGiven:MutableLiveData<Boolean> = MutableLiveData(false)
    var discount:Discount? = null

    init {
        mockDiscount()
    }

    private fun mockDiscount() {
        discount = Discount()
        discount!!.amountToBePaid = "390"
        discount!!.discountAmount = "10"
    }

    fun updateUpiDiscountGiven(flag:Boolean)
    {
        upiDiscountGiven.value = flag
    }

}

class Discount
{
    var amountToBePaid:String? = ""
    var discountAmount:String? = ""
}