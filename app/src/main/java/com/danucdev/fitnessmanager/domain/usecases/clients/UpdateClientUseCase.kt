package com.danucdev.fitnessmanager.domain.usecases.clients

import com.danucdev.fitnessmanager.domain.models.Client
import com.danucdev.fitnessmanager.domain.repositories.ClientRepository
import javax.inject.Inject

class UpdateClientUseCase @Inject constructor(private val clientRepository: ClientRepository) {

    suspend operator fun invoke(client:Client) {

        val name = client.name.trim()
        val lastName = client.lastName.trim()

        clientRepository.updateClient(client.copy(name = name, lastName = lastName))

    }

}