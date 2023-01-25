package com.example.simplebasketapp.domain

import com.example.simplebasketapp.data.model.AddBasketRequestModel
import com.example.simplebasketapp.data.model.OrderResponseModel
import com.example.simplebasketapp.data.model.RequestModelObject
import com.example.simplebasketapp.ui.adapter.ProductLisItemUiModel
import com.example.simplebasketapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface BasketRepository {
    suspend fun getProductList() : Flow<Resource<List<ProductLisItemUiModel>>>

    suspend fun postOrder(requestModel: RequestModelObject) : Flow<Resource<OrderResponseModel>>
}