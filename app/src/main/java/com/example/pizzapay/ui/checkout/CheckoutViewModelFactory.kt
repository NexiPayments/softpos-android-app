package com.example.pizzapay.ui.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CheckoutViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CheckoutViewModel::class.java)) {
            return CheckoutViewModel() as T
        }

        throw IllegalArgumentException("Unknown CheckoutViewModel class")
    }
}