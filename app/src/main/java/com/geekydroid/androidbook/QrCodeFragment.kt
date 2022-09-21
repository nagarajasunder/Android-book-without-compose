package com.geekydroid.androidbook

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import com.geekydroid.androidbook.databinding.FragmentQrCodeBinding
import com.geekydroid.androidbook.viewmodels.QrCodeViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "xyz - QrCodeFragment"
@AndroidEntryPoint
class QrCodeFragment : Fragment() {

    private val viewmodel: QrCodeViewModel by viewModels()
    private var paymentStatusResponse = MutableLiveData<UpiStatusResponse?>()
    private lateinit var binding:FragmentQrCodeBinding

    companion object {
        fun newInstance() = QrCodeFragment()
    }

    private lateinit var viewModel: QrCodeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_qr_code,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val response = UpiStatusResponse()
        response.status = "Pending"
        response.amountPaid = 0
        paymentStatusResponse.value = response

        viewmodel.upiPaymentResponse.observe(viewLifecycleOwner){ resp ->
            Log.d(TAG, "onViewCreated: observing response")
            if (resp?.status.equals("Success",true))
            {
                Log.d(TAG, "onViewCreated: Success")
                paymentStatusResponse.value = resp
                goBack()
            }
        }
        Log.d(TAG, "onViewCreated: Calling the observer")
        viewmodel.getUpiPaymentStatus()
    }

    private fun goBack() {
        (requireActivity() as DaasActivity).supportFragmentManager.popBackStack()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(QrCodeViewModel::class.java)

    }

    fun updateUpiStatusResponse(value:MutableLiveData<UpiStatusResponse?>)
    {
        paymentStatusResponse = value
    }
}