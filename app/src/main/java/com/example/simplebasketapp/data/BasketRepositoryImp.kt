package com.example.simplebasketapp.data

import com.example.simplebasketapp.domain.BasketRepository
import com.example.simplebasketapp.ui.adapter.ProductLisItemUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BasketRepositoryImp @Inject constructor(private val basketAppService: BasketAppService) : BasketRepository {
    override suspend fun getProductList(): List<ProductLisItemUiModel> {
        val productList = withContext(Dispatchers.IO) {
            basketAppService.getProductList()
        }

        return productList.map {
            ProductLisItemUiModel(
                it.id.toString(),
                it.name,
                it.price,
                it.currency,
                it.image
            )
        }
    }


}