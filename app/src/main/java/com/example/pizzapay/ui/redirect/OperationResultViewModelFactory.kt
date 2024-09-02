package com.example.pizzapay.ui.redirect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class OperationResultViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OperationResultViewModel::class.java)) {
            return OperationResultViewModel() as T
        }

        throw IllegalArgumentException("Unknown OperationResultViewModel class")
    }
}