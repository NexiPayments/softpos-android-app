package com.example.pizzapay.ui.redirect

data class RevertResult(
    val isRevertSuccess: Boolean = false,
    val exceptionString: String = "",
    val isSuccessString: String = ""
)