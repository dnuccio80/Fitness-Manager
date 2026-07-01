package com.danucdev.fitnessmanager.domain.usecases.clients

import com.danucdev.fitnessmanager.domain.models.Transaction
import com.danucdev.fitnessmanager.domain.repositories.ClientRepository
import com.danucdev.fitnessmanager.domain.repositories.TransactionRepository
import com.danucdev.fitnessmanager.ui.screens.clients.addclients.ClientData
import javax.inject.Inject

class AddClientUseCase @Inject constructor(
    private val clientRepo: ClientRepository,
    private val transactionRepo: TransactionRepository
) {
    suspend operator fun invoke(clientData: ClientData) {

        val name = clientData.name.trim()
        val lastName = clientData.lastName.trim()


        if(clientData.alreadyPay) {
            val transaction = Transaction(
                isEarn = true,
                amount = 40000,
                description = "Inscripción en ${clientData.paymentMethod.method} de $name $lastName"
            )

            transactionRepo.addTransaction(transaction)
        }

        val client = clientData.copy(name = name, lastName = lastName).toClient()

        clientRepo.addClient(client)
    }
}