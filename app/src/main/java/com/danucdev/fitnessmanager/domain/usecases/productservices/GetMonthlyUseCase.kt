package com.danucdev.fitnessmanager.domain.usecases.productservices

import com.danucdev.fitnessmanager.domain.models.ProductService
import com.danucdev.fitnessmanager.domain.repositories.ProductServiceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetMonthlyUseCase @Inject constructor(private val repository: ProductServiceRepository) {
    operator fun invoke():Flow<ProductService>  {
        return repository.getProductServiceById(1)
    }
}