package com.danucdev.fitnessmanager.ui.screens.clients.productservices

import com.danucdev.fitnessmanager.domain.models.ProductService
import com.danucdev.fitnessmanager.domain.repositories.ProductServiceRepository
import javax.inject.Inject

class AddProductServicesUseCase @Inject constructor(private val repository: ProductServiceRepository) {
    suspend operator fun invoke(productService: ProductService) {

        val trimLabel = productService.label.trim()

        repository.addProductService(productService.copy(label = trimLabel))
    }
}