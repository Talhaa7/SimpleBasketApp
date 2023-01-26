package com.example.simplebasketapp.utils

import android.view.View
import com.example.simplebasketapp.ui.adapter.ProductLisItemUiModel

interface ProductButtonClickListener {
    fun onProductListButtonClickListener(productListUiModel: ProductLisItemUiModel)
}