package com.danucdev.fitnessmanager.domain.usecases.main

import com.danucdev.fitnessmanager.domain.repositories.TransactionRepository
import com.danucdev.fitnessmanager.ui.screens.main.MainData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.math.exp

class GetTransactionsResumeUseCase @Inject constructor(private val transactionRepository: TransactionRepository) {

    operator fun invoke(): Flow<MainData> {

        return transactionRepository.getAllTransactions()
            .map { transactions ->

                var earns = 0
                var expenses = 0

                transactions.forEach { transaction ->

                    val amount = transaction.amount.toIntOrNull() ?: 0

                    if (transaction.isEarn) {
                        earns += amount
                    } else {
                        expenses += amount
                    }

                }

                MainData(
                    totalEarns = earns.toString(),
                    totalExpenses = expenses.toString()
                )

            }

    }
}