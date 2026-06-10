package com.danucdev.fitnessmanager.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.danucdev.fitnessmanager.data.entities.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Query("SELECT * FROM TransactionEntity ORDER BY id DESC")
    fun getAllTransactions(): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM TransactionEntity WHERE id = :id")
    fun getTransactionById(id:Int): Flow<TransactionEntity>

    @Insert(onConflict = REPLACE)
    suspend fun addTransaction(transaction: TransactionEntity)

    @Query("DELETE FROM transactionentity WHERE id = :id")
    suspend fun deleteTransactionById(id:Int)

    @Update
    suspend fun updateTransaction(transaction: TransactionEntity)

}