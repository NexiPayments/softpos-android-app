package com.example.pizzapay.ui.checkout

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.nexipos.app2app.App2AppIPC
import it.nexipos.app2app.data.model.A2ASDKResponse
import it.nexipos.app2app.data.model.PaymentData
import it.nexipos.app2app.listener.OperationListener
import kotlinx.coroutines.launch

class CheckoutViewModel() : ViewModel() {

    private val _payResult = MutableLiveData<PayResult>()
    val payResult: LiveData<PayResult> = _payResult

    fun pay(
        app2app: App2AppIPC,
        amount: Int,
        addInfo1: String,
        addInfo2: String,
        addInfo3: String,
        addInfo4: String,
        addInfo5: String,
        email: String,
        sms: String,
        sendTicket: Boolean,
        urlTicket: Boolean
    ) {
        viewModelScope.launch {
            // Start Nexi Payment with value received from UI
            startPayment(
                app2app,
                amount.toString(),
                System.currentTimeMillis().toString(),
                addInfo1,
                addInfo2,
                addInfo3,
                addInfo4,
                addInfo5,
                email,
                sms,
                sendTicket,
                urlTicket
            )
        }
    }

    private suspend fun startPayment(
        app2app: App2AppIPC,
        amount: String,
        callerTrxId: String,
        addInfo1: String,
        addInfo2: String,
        addInfo3: String,
        addInfo4: String,
        addInfo5: String,
        email: String,
        sms: String,
        sendTicket: Boolean,
        urlTicket: Boolean
    ) {
        // Payment details
        val paymentData = PaymentData(
            amount, // Transaction amount
            callerTrxId, // Transaction ID
            sendTicket, // Flag that enables the display of the button "INVIA RICEVUTA" on Nexi POS app
            urlTicket, // Flag that enables the creation of the link to obtain the receipt image
            "PIZZA PAY", // String used to value the label of the "TORNA A ..." button
            addInfo1, // Transaction additional info
            addInfo2, // Transaction additional info
            addInfo3, // Transaction additional info
            addInfo4, // Transaction additional info
            addInfo5, // Transaction additional info
            email, // Customer email
            sms // Customer phone number
        )

        // Listener used to manage payment initialization
        val operationListener = (object : OperationListener {
            override fun onFailure(error: A2ASDKResponse?) {
                Log.d("APP_DEBUG", "pay operationListener onFailure")

                _payResult.postValue(
                    PayResult(
                        isPaymentSuccess = false,
                        error?.exception.toString(),
                        error?.isSuccess.toString()
                    )
                )
            }

            override fun onSuccess(response: A2ASDKResponse?) {
                Log.d("APP_DEBUG", "pay operationListener onSuccess")

                _payResult.postValue(PayResult(isPaymentSuccess = true, "", ""))
            }
        })

        Log.d("APP_DEBUG", "Start SDK pay")

        // Start payment flow
        app2app.pay(paymentData, operationListener)
    }

}