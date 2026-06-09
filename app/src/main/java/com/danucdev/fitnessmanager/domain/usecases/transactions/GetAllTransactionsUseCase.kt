package com.danucdev.fitnessmanager.domain.usecases.transactions

import com.danucdev.fitnessmanager.domain.models.Transaction
import com.danucdev.fitnessmanager.domain.repositories.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllTransactionsUseCase @Inject constructor(private val transactionRepository: TransactionRepository){
    operator fun invoke(): Flow<List<Transaction>> = transactionRepository.getAllTransactions()
}