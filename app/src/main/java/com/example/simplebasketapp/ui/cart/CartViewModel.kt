package com.example.simplebasketapp.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplebasketapp.data.model.AddBasketRequestModel
import com.example.simplebasketapp.data.model.RequestModelObject
import com.example.simplebasketapp.domain.BasketRepository
import com.example.simplebasketapp.ui.adapter.ProductLisItemUiModel
import com.example.simplebasketapp.ui.cart.adapter.CartListItemUiModel
import com.example.simplebasketapp.utils.Resource
import com.example.simplebasketapp.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val basketRepository: BasketRepository
): ViewModel(){

    private val _productList = MutableLiveData<List<ProductLisItemUiModel>> ()

    val productList : LiveData<List<ProductLisItemUiModel>>
        get() = _productList

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private var _errorString = SingleLiveEvent<String>()
    val errorString: LiveData<String>
        get() = _errorString

    private var _clearBasket = MutableLiveData<Boolean>()
    val clearBasket : LiveData<Boolean>
        get() = _clearBasket


    fun postOrder(cartListItemUiModel: List<CartListItemUiModel>) {
        val postRequestModel = RequestModelObject()

        cartListItemUiModel.map {
            postRequestModel.add(
                AddBasketRequestModel(
                    it.id.toInt(),
                    it.qty
                )
            )
        }


        viewModelScope.launch {
            basketRepository.postOrder(postRequestModel)
                .collect{ resource ->
                    when (resource) {
                        is Resource.Loading -> {

                            _isLoading.value = true
                            _clearBasket.value = false
                        }
                        is Resource.Success -> {
                            _isLoading.value = false
                            resource.data?.let {
                                _errorString.value = "Order Successful And Basket Cleared"
                                _clearBasket.value = true
                            }
                        }
                        is Resource.Error -> {
                            _isLoading.value = false
                            if (resource.message == "404") {
                                _errorString.value = "Out of stock"
                                _clearBasket.value = false
                            } else {
                                _errorString.value = "Unexpected Error"
                                _clearBasket.value = false
                            }

                        }
                    }
                }
        }
    }


}