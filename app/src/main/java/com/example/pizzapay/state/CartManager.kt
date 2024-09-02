package com.example.pizzapay.state

import com.example.pizzapay.ui.cart.CartItemModel
import com.example.pizzapay.ui.productlist.ProductItemModel

object CartManager {
    private val _cartItems = mutableMapOf<String, CartItemModel>()

    val cartItems: Map<String, CartItemModel> get() = _cartItems

    fun addProduct(product: ProductItemModel) {
        if (product.id in _cartItems) {
            _cartItems.replace(
                product.id, CartItemModel(_cartItems[product.id]?.qty?.plus(1) ?: 1, product)
            )
        } else {
            _cartItems[product.id] = CartItemModel(1, product)
        }
    }

    fun removeProduct(product: ProductItemModel) {
        if (product.id in _cartItems) {
            if (_cartItems[product.id]?.qty!! > 1) {
                _cartItems.replace(
                    product.id, CartItemModel(_cartItems[product.id]?.qty?.minus(1) ?: 1, product)
                )
            } else {
                clearProduct(product)
            }
        }
    }

    fun clearProduct(product: ProductItemModel) {
        if (product.id in _cartItems) {
            _cartItems.remove(product.id)
        }
    }

    fun clearCart() {
        _cartItems.clear()
    }
}