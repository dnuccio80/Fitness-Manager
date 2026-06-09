package com.danucdev.fitnessmanager.domain.usecases.transactions

import com.danucdev.fitnessmanager.domain.models.Transaction
import com.danucdev.fitnessmanager.domain.repositories.TransactionRepository
import javax.inject.Inject

class AddTransactionUseCase @Inject constructor(private val transactionRepository: TransactionRepository) {

    suspend operator fun invoke(transaction: Transaction) = transactionRepository.addTransaction(transaction)

}