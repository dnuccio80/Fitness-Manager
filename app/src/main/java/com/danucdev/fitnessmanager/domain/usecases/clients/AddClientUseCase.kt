package com.danucdev.fitnessmanager.domain.usecases.clients

import com.danucdev.fitnessmanager.domain.models.Client
import com.danucdev.fitnessmanager.domain.repositories.ClientRepository
import javax.inject.Inject

class AddClientUseCase @Inject constructor(private val repository: ClientRepository) {
    suspend operator fun invoke(client: Client) {
        repository.addClient(client)
    }
}