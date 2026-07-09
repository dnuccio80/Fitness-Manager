package com.danucdev.fitnessmanager.domain.usecases.clients

import com.danucdev.fitnessmanager.domain.models.Transaction
import com.danucdev.fitnessmanager.domain.repositories.ClientRepository
import com.danucdev.fitnessmanager.domain.repositories.ProductServiceRepository
import com.danucdev.fitnessmanager.domain.repositories.TransactionRepository
import com.danucdev.fitnessmanager.ui.screens.clients.addclients.ClientData
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AddClientUseCase @Inject constructor(
    private val clientRepo: ClientRepository,
    private val transactionRepo: TransactionRepository,
    private val productServiceRepository: ProductServiceRepository,
) {
    suspend operator fun invoke(clientData: ClientData) {

        val name = clientData.name.trim()
        val lastName = clientData.lastName.trim()


        if (clientData.alreadyPay) {

            val monthlyValue = productServiceRepository.getProductServiceById(1).first().amount

            val transaction = Transaction(
                isEarn = true,
                amount = monthlyValue,
                description = "Inscripción en ${clientData.paymentMethod.method} de $name $lastName"
            )

            transactionRepo.addTransaction(transaction)
        }

        val client = clientData.copy(name = name, lastName = lastName).toClient()

        clientRepo.addClient(client)
    }
}