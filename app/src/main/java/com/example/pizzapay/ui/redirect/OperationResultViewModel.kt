package com.example.pizzapay.ui.redirect

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.nexipos.app2app.App2AppIPC
import it.nexipos.app2app.data.model.A2ASDKResponse
import it.nexipos.app2app.data.model.ReversalData
import it.nexipos.app2app.listener.OperationListener
import kotlinx.coroutines.launch

class OperationResultViewModel() : ViewModel() {

    private val _revertResult = MutableLiveData<RevertResult>()
    val revertResult: LiveData<RevertResult> = _revertResult

    fun revert(app2app: App2AppIPC, amount: Int, terminalId: String) {
        viewModelScope.launch {
            val reversalData = ReversalData(
                amount.toString(),
                System.currentTimeMillis().toString(),
                "PIZZA PAY",
                null,
                null,
                System.currentTimeMillis().toString(),
                terminalId,
                true,
                true
            )

            val operationListener = (object : OperationListener {
                override fun onFailure(error: A2ASDKResponse?) {
                    Log.d("APP_DEBUG", "revert operationListener onFailure")

                    _revertResult.postValue(
                        RevertResult(
                            isRevertSuccess = false,
                            error?.exception.toString(),
                            error?.isSuccess.toString()
                        )
                    )
                }

                override fun onSuccess(response: A2ASDKResponse?) {
                    Log.d("APP_DEBUG", "revert operationListener onSuccess")

                    _revertResult.postValue(RevertResult(isRevertSuccess = true, "", ""))
                }
            })

            Log.d("APP_DEBUG", "Start SDK revert")

            app2app.revert(reversalData, operationListener)
        }
    }

}