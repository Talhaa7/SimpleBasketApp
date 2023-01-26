package com.example.simplebasketapp.ui.cart.adapter

data class CartListItemUiModel(
    val id: String,
    val name: String,
    val price: String,
    val currency: String,
    val image: String,
    val qty: Int = 1
)
