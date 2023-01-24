package com.example.simplebasketapp.domain

import com.example.simplebasketapp.ui.adapter.ProductLisItemUiModel

interface BasketRepository {
    suspend fun getProductList() : List<ProductLisItemUiModel>
}