package com.danucdev.fitnessmanager.domain.usecases.clients

import com.danucdev.fitnessmanager.domain.repositories.ClientRepository
import com.danucdev.fitnessmanager.ui.screens.clients.clientlist.ClientListData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetClientsResumeUseCase @Inject constructor(private val clientRepository: ClientRepository) {

    operator fun invoke():Flow<ClientListData> {

        return clientRepository.getAllClients().map { clientsList ->

            var totalClients = 0
            var activeClients = 0
            var inactiveClients = 0

            clientsList.forEach {
                totalClients++
                // NOW WE GIVE TOTAL CLIENTS BUT WE MUST TO ADD JUST ACTIVES
                activeClients++
            }

            ClientListData(
                clientsQuantity = totalClients,
                clientList = clientsList,
                activeClients = activeClients,
                inactiveClients = 13
            )
        }
    }

}