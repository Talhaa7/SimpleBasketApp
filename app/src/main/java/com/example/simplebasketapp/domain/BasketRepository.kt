package com.example.simplebasketapp.domain

import com.example.simplebasketapp.ui.adapter.ProductLisItemUiModel
import com.example.simplebasketapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface BasketRepository {
    suspend fun getProductList() : Flow<Resource<List<ProductLisItemUiModel>>>
}