package com.example.simplebasketapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplebasketapp.domain.BasketRepository
import com.example.simplebasketapp.ui.adapter.ProductLisItemUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val basketRepository: BasketRepository
): ViewModel() {

    private val _productList = MutableLiveData<List<ProductLisItemUiModel>> ()

    val productList : LiveData<List<ProductLisItemUiModel>>
        get() = _productList

    init {
        getProductList()
    }

    fun getProductList() {
        viewModelScope.launch {
            val productListResponse = basketRepository.getProductList()
            _productList.value = productListResponse
        }
    }

}