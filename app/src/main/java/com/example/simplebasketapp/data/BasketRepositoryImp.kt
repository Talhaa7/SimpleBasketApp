package com.example.simplebasketapp.data

import com.example.simplebasketapp.data.model.AddBasketRequestModel
import com.example.simplebasketapp.data.model.OrderResponseModel
import com.example.simplebasketapp.data.model.RequestModelObject
import com.example.simplebasketapp.domain.BasketRepository
import com.example.simplebasketapp.ui.adapter.ProductLisItemUiModel
import com.example.simplebasketapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class BasketRepositoryImp @Inject constructor(private val basketAppService: BasketAppService) : BasketRepository {
    override suspend fun getProductList(): Flow<Resource<List<ProductLisItemUiModel>>> {

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

    override suspend fun postOrder(requestModel: RequestModelObject): Flow<Resource<OrderResponseModel>> {
        return flow {

            try {
                emit(Resource.Loading(true))
                val orderResponse = basketAppService.postOrder(requestModel)

                emit(Resource.Success(
                    data = orderResponse
                ))
            } catch (e: HttpException) {
                if (e.code() == 404) {
                    emit(Resource.Error(
                        "404"
                    ))
                } else {
                    emit(Resource.Error("other"))
                }
            }


        }
    }


}