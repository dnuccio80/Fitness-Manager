package com.danucdev.fitnessmanager.ui.screens.productservices

import com.danucdev.fitnessmanager.domain.models.ProductService

data class ProductServiceUiState(
    val isLoading: Boolean,
    val productServicesList: List<ProductService>? = null,
    val newProductServiceData: ProductService = ProductService(
        label = "",
        amount = 0L
    )
)

