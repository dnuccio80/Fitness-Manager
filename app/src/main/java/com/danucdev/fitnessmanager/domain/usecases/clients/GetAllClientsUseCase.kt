package com.danucdev.fitnessmanager.domain.usecases.clients

import com.danucdev.fitnessmanager.domain.models.Client
import com.danucdev.fitnessmanager.domain.repositories.ClientRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllClientsUseCase @Inject constructor(private val clientRepository: ClientRepository) {
    operator fun invoke(): Flow<List<Client>> = clientRepository.getAllClients()
}