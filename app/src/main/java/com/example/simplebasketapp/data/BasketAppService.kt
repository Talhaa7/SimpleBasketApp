package com.example.simplebasketapp.data

import com.example.simplebasketapp.data.model.ProductResponse
import com.example.simplebasketapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface BasketAppService {

    companion object {
        const val BASE_URL = "https://nonchalant-fang.glitch.me/"
    }

    @GET("listing")
    suspend fun getProductList(): ProductResponse
}