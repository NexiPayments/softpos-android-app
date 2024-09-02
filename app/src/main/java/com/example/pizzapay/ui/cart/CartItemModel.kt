package com.example.pizzapay.ui.cart

import com.example.pizzapay.ui.productlist.ProductItemModel

data class CartItemModel(
    val qty: Int,
    val product: ProductItemModel
)