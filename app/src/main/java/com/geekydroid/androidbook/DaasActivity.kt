package com.geekydroid.androidbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "xyz - DaasActivity"
@AndroidEntryPoint
class DaasActivity : AppCompatActivity() {

    private var upiDiscountInitiated = false
    private var upiPaymentResponse:MutableLiveData<UpiStatusResponse?> = MutableLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daas)

        openPaymentFragment()
    }

    fun updateUpiDiscountInitiated(flag:Boolean)
    {
        upiDiscountInitiated = flag
    }

    fun isUpiDiscountInitiated():Boolean = upiDiscountInitiated

    fun navigateToQrCodeFragment() {
        Log.d(TAG, "navigateToQrCodeFragment: called")
        val fragment = QrCodeFragment.newInstance()
        fragment.updateUpiStatusResponse(upiPaymentResponse)
        val transaction = supportFragmentManager.beginTransaction()
        fragment.let {
            transaction.add(R.id.fragment_container,it)
            transaction.addToBackStack(null)
        }
        Log.d(TAG, "navigateToQrCodeFragment: opening QrCodeFragment")
        transaction.commit()
    }

    fun openPaymentFragment()
    {
        val fragment = PaymentFragment.newInstance()
        fragment.updateUpiStatusResponse(upiPaymentResponse)
        val transaction = supportFragmentManager.beginTransaction()
        fragment.let {
            transaction.add(R.id.fragment_container,it)
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }
}

class UpiStatusResponse {
    var status:String? = ""
    var amountPaid:Int? = 0
}