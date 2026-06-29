package com.danucdev.fitnessmanager.domain.repositories

import com.danucdev.fitnessmanager.domain.models.ProductService
import kotlinx.coroutines.flow.Flow

interface ProductServiceRepository {
    fun getAllProductServices():Flow<List<ProductService>>
    suspend fun getProductServiceById(id:Int):Flow<ProductService>
    suspend fun addProductService(productService: ProductService)
    suspend fun updateProductService(productService: ProductService)
    suspend fun deleteProductServiceById(id:Int)
}