package com.example.simplebasketapp.utils

interface CartItemClickListener {
    fun increaseProductNumber(id: String)
    fun decreaseProductNumber(id: String)
    fun removeProductFromList(id: String)
}