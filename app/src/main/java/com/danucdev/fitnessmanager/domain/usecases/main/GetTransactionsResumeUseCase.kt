package com.danucdev.fitnessmanager.domain.usecases.main

import com.danucdev.fitnessmanager.domain.repositories.ClientRepository
import com.danucdev.fitnessmanager.domain.repositories.TransactionRepository
import com.danucdev.fitnessmanager.ui.screens.main.MainData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.math.exp

class GetTransactionsResumeUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val clientRepository: ClientRepository,
) {

    operator fun invoke(): Flow<MainData> {

        return combine(
            transactionRepository.getAllTransactions(),
            clientRepository.getAllClients()
        ) {transactions, clients ->

            var earns = 0L
            var expenses = 0L

            transactions.forEach { transaction ->
                val amount = transaction.amount.toLong()
                if (transaction.isEarn) {
                    earns += amount
                } else {
                    expenses += amount
                }
            }

            MainData(
                totalEarns = earns,
                totalExpenses = expenses,
                activeClients = clients.count()
            )

        }
    }
}