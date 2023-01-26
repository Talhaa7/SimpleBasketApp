package com.example.simplebasketapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.simplebasketapp.ui.cart.adapter.CartListItemUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor() : ViewModel() {

    private val _productList = MutableLiveData<List<CartListItemUiModel>> ()

    val productList : LiveData<List<CartListItemUiModel>>
        get() = _productList

    fun addCartItem(cartListItemUiModel: CartListItemUiModel) {

        val product = (_productList.value ?: mutableListOf()).toMutableList()


        if (product.none {
            it.id == cartListItemUiModel.id
        })
        {
            product.add(cartListItemUiModel)
            _productList.value = product
        }

    }

    fun increaseProductNumber(id : String) {
        val product = (_productList.value ?: mutableListOf()).toMutableList()
        val index = product.indexOfFirst { it.id == id }

        product[index] = product[index].copy(
            qty = product[index].qty + 1
        )

        _productList.value = product
    }

    fun decreaseProductNumber(id : String) {

        val product = (_productList.value ?: mutableListOf()).toMutableList()
        val index = product.indexOfFirst { it.id == id }

        product[index] = product[index].copy(
            qty = product[index].qty - 1
        )

        if (product[index].qty == 0)
        {
            product.removeAt(index)
        }

        _productList.value = product

    }

    fun removeProductFromList(id : String) {

        val product = (_productList.value ?: mutableListOf()).toMutableList()
        val index = product.indexOfFirst { it.id == id }
        product.removeAt(index)

        _productList.value = product
    }

    fun clearBasket() {
        val product = (_productList.value ?: mutableListOf()).toMutableList()
        product.clear()
        _productList.value = product
    }
}