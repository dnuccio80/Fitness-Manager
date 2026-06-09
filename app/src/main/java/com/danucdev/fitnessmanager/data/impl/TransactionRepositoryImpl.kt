package com.danucdev.fitnessmanager.data.impl

import com.danucdev.fitnessmanager.data.dao.TransactionDao
import com.danucdev.fitnessmanager.domain.models.Transaction
import com.danucdev.fitnessmanager.domain.repositories.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(private val transactionDao: TransactionDao): TransactionRepository {
    override fun getAllTransactions(): Flow<List<Transaction>> {
        return transactionDao.getAllTransactions().map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun getTransactionById(id: Int): Flow<Transaction> {
        return transactionDao.getTransactionById(id).map { it.toDomain() }
    }

    override suspend fun addTransaction(transaction: Transaction) {
        transactionDao.addTransaction(transaction.toEntity())
    }

    override suspend fun deleteTransactionById(id: Int) {
        transactionDao.deleteTransactionById(id)
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        transactionDao.updateTransaction(transaction.toEntity())
    }
}