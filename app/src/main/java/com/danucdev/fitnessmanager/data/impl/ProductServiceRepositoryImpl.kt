package com.danucdev.fitnessmanager.data.impl

import com.danucdev.fitnessmanager.data.dao.ProductServiceDao
import com.danucdev.fitnessmanager.domain.models.ProductService
import com.danucdev.fitnessmanager.domain.repositories.ProductServiceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductServiceRepositoryImpl @Inject constructor(private val productServiceDao: ProductServiceDao) : ProductServiceRepository {
    override fun getAllProductServices(): Flow<List<ProductService>> {
        return productServiceDao.getAllProductServices().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun getProductServiceById(id: Int): Flow<ProductService> {
        return productServiceDao.getProductServiceById(id).map { it.toDomain() }
    }

    override suspend fun addProductService(productService: ProductService) {
        productServiceDao.addProductService(productService.toEntity())
    }

    override suspend fun updateProductService(productService: ProductService) {
        productServiceDao.updateProductService(productService.toEntity())
    }

    override suspend fun deleteProductServiceById(id: Int) {
        productServiceDao.deleteProductServiceById(id)
    }
}