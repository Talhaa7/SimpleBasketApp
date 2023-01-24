package com.example.simplebasketapp.ui

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplebasketapp.domain.BasketRepository
import com.example.simplebasketapp.ui.adapter.ProductLisItemUiModel
import com.example.simplebasketapp.utils.Resource
import com.example.simplebasketapp.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val basketRepository: BasketRepository
): ViewModel() {

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _productList = MutableLiveData<List<ProductLisItemUiModel>> ()

    val productList : LiveData<List<ProductLisItemUiModel>>
        get() = _productList

    private var _errorString = SingleLiveEvent<String>()
    val errorString: LiveData<String>
        get() = _errorString

    init {
        getProductList()
    }

    fun getProductList() {
        viewModelScope.launch {
            basketRepository.getProductList()
                .collect{ resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            _isLoading.value = true
                        }
                        is Resource.Success -> {
                            _isLoading.value = false
                            resource.data?.let {
                                _productList.value = it
                            }
                        }
                        is Resource.Error -> {
                            _isLoading.value = false
                            _errorString.value = resource.message
                        }
                    }
                }
        }
    }
}