package com.example.simplebasketapp.data

import com.example.simplebasketapp.domain.BasketRepository
import com.example.simplebasketapp.ui.adapter.ProductLisItemUiModel
import com.example.simplebasketapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BasketRepositoryImp @Inject constructor(private val basketAppService: BasketAppService) : BasketRepository {
    override suspend fun getProductList(): Flow<Resource<List<ProductLisItemUiModel>>> {
        /*val productList = withContext(Dispatchers.IO) {
            basketAppService.getProductList()
        }*/

        return flow {
            emit(Resource.Loading(true))
            val productList = basketAppService.getProductList()

            emit(Resource.Success(
                data = productList.map {
                    ProductLisItemUiModel(
                        it.id.toString(),
                        it.name,
                        it.price,
                        it.currency,
                        it.image
                    )
                }
            ))

        }
    }


}