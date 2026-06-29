package com.danucdev.fitnessmanager.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.danucdev.fitnessmanager.domain.models.ProductService

@Entity
data class ProductServiceEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val label:String,
    val amount:Long
) {
    fun toDomain(): ProductService {
        return ProductService(
            id = id,
            label = label,
            amount = amount
        )
    }
}

