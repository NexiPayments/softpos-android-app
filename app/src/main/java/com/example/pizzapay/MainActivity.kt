package com.example.pizzapay

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.pizzapay.ui.cart.CartViewModel
import com.example.pizzapay.ui.cart.CartViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var cartViewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContentView(R.layout.activity_main)

        cartViewModel = ViewModelProvider(
            this,
            CartViewModelFactory()
        )[CartViewModel::class.java]

        if (intent.getBooleanExtra("CLEAR_CART", false)) {
            cartViewModel.clearCart()
        }
    }

}