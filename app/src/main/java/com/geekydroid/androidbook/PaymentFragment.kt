package com.geekydroid.androidbook

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import com.geekydroid.androidbook.databinding.FragmentPaymentBinding
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "xyz - PaymentFragment"
@AndroidEntryPoint
class PaymentFragment : Fragment() {


    companion object
    {
        fun newInstance():PaymentFragment = PaymentFragment()
    }

    private val viewModel: PaymentViewModel by viewModels()
    private lateinit var binding:FragmentPaymentBinding
    private var upiStatusResponse = MutableLiveData<UpiStatusResponse?>()

    override fun onResume() {
        Log.d(TAG, "onResume: called")
        super.onResume()
        upiStatusResponse.observe(viewLifecycleOwner){
            updateUpiMessage(it)
        }
    }


    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: called")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView: called")
    }


    private fun updateUpiMessage(status: UpiStatusResponse?) {
        Log.d(TAG, "updateUpiMessage: Called")
        if (status?.status.equals("Success",true))
        {
            Log.d(TAG, "updateUpiMessage: payment success")
            val initiated = (requireActivity() as DaasActivity).isUpiDiscountInitiated()
            if (!viewModel.upiDiscountGiven.value!! && initiated)
            {
                binding.etPaymentAmount.editText?.setText("${(status?.amountPaid?:0)+((viewModel.discount?.discountAmount?:"0").toInt())}")
                Toast.makeText(requireContext(),"Upi payment discount success ful",Toast.LENGTH_SHORT).show()
                viewModel.updateUpiDiscountGiven(true)
                Log.d(TAG, "updateUpiMessage: upi discount given")
                Log.d(TAG, "updateUpiMessage: Amount paid ${status?.amountPaid} discount ${viewModel.discount?.discountAmount}")
            }
        }
        else
        {
//            (requireActivity() as DaasActivity).updateUpiDiscountInitiated(false)
//            Log.d(TAG, "updateUpiMessage: upi discount initiated false")
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_payment,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnPay.setOnClickListener {
            openQrCodeFragment()
        }

    }

    private fun openQrCodeFragment() {
        val discountAmount = (viewModel.discount?.discountAmount?:"0").toInt()
        if (discountAmount > 0)
        {
            (requireActivity() as DaasActivity).updateUpiDiscountInitiated(true)
        }
        else
        {
            (requireActivity() as DaasActivity).updateUpiDiscountInitiated(false)
        }

        (requireActivity() as DaasActivity).navigateToQrCodeFragment()
    }

    fun updateUpiStatusResponse(value:MutableLiveData<UpiStatusResponse?>)
    {
        upiStatusResponse = value
    }

}