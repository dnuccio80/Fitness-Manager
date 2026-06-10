package com.danucdev.fitnessmanager.domain.usecases.transactions

import com.danucdev.fitnessmanager.domain.models.Transaction
import com.danucdev.fitnessmanager.domain.repositories.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import javax.inject.Inject

class GetLastTransactionsUseCase @Inject constructor(private val transactionRepository: TransactionRepository) {

    operator fun invoke():Flow<List<Transaction>> {
        return transactionRepository.getAllTransactions().map {
            it.take(4)
        }
    }

}