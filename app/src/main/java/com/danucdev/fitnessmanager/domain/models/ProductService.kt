package com.danucdev.fitnessmanager.domain.models

import com.danucdev.fitnessmanager.data.entities.ProductServiceEntity

data class ProductService(
    val id:Int = 0,
    val label:String,
    val amount:Long
) {
    fun toEntity(): ProductServiceEntity {
        return ProductServiceEntity(
            id = id,
            label = label,
            amount = amount
        )
    }
}
