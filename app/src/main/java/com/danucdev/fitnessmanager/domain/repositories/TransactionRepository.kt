package com.danucdev.fitnessmanager.domain.repositories

import com.danucdev.fitnessmanager.domain.models.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    fun getAllTransactions():Flow<List<Transaction>>
    fun getTransactionById(id:Int):Flow<Transaction>
    suspend fun addTransaction(transaction: Transaction)
    suspend fun deleteTransactionById(id:Int)
    suspend fun updateTransaction(transaction: Transaction)
}