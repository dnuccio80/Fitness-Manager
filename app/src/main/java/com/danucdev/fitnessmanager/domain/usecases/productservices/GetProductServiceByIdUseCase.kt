package com.danucdev.fitnessmanager.domain.usecases.productservices

import com.danucdev.fitnessmanager.domain.models.ProductService
import com.danucdev.fitnessmanager.domain.repositories.ProductServiceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetProductServiceByIdUseCase @Inject constructor(private val productServiceRepository: ProductServiceRepository) {
    suspend operator fun invoke(id:Int):ProductService =  productServiceRepository.getProductServiceById(id).first()
}