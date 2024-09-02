package com.example.pizzapay.ui.checkout

data class PayResult(
    val isPaymentSuccess: Boolean = false,
    val exceptionString: String = "",
    val isSuccessString: String = ""
)