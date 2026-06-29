package com.danucdev.fitnessmanager.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.danucdev.fitnessmanager.data.entities.ProductServiceEntity
import com.danucdev.fitnessmanager.domain.models.ProductService
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductServiceDao {

    @Query("SELECT * FROM ProductServiceEntity ORDER BY label ASC")
    fun getAllProductServices(): Flow<List<ProductServiceEntity>>

    @Query("SELECT * FROM ProductServiceEntity WHERE id = :id")
    fun getProductServiceById(id:Int): Flow<ProductServiceEntity>

    @Update
    suspend fun updateProductService(productService: ProductServiceEntity)

    @Query("DELETE FROM PRODUCTSERVICEENTITY WHERE id = :id")
    suspend fun deleteProductServiceById(id:Int)

    @Insert(onConflict = REPLACE)
    suspend fun addProductService(productService: ProductServiceEntity)
}