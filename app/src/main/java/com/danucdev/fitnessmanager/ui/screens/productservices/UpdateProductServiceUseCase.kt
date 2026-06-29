package com.danucdev.fitnessmanager.ui.screens.productservices

import com.danucdev.fitnessmanager.domain.models.ProductService
import com.danucdev.fitnessmanager.domain.repositories.ProductServiceRepository
import javax.inject.Inject

class UpdateProductServiceUseCase @Inject constructor(private val productServiceRepository: ProductServiceRepository){
    suspend operator fun invoke(productService: ProductService) = productServiceRepository.updateProductService(productService)
}