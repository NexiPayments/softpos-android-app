package com.example.pizzapay.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pizzapay.state.CartManager
import com.example.pizzapay.ui.productlist.ProductItemModel

class CartViewModel : ViewModel() {

    private val _cartItems = MutableLiveData<Map<String, CartItemModel>>()
    val cartItems: LiveData<Map<String, CartItemModel>> get() = _cartItems

    init {
        _cartItems.value = CartManager.cartItems
    }

    fun addProduct(product: ProductItemModel) {
        CartManager.addProduct(product)
        _cartItems.value = CartManager.cartItems
    }

    fun removeProduct(product: ProductItemModel) {
        CartManager.removeProduct(product)
        _cartItems.value = CartManager.cartItems
    }

    fun clearProduct(product: ProductItemModel) {
        CartManager.clearProduct(product)
        _cartItems.value = CartManager.cartItems
    }

    fun clearCart() {
        CartManager.clearCart()
        _cartItems.value = CartManager.cartItems
    }

}
