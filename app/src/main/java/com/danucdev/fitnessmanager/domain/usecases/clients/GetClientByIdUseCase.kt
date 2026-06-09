package com.danucdev.fitnessmanager.domain.usecases.clients

import com.danucdev.fitnessmanager.domain.models.Client
import com.danucdev.fitnessmanager.domain.repositories.ClientRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetClientByIdUseCase @Inject constructor(private val repository: ClientRepository) {
    operator fun invoke(clientId:Int): Flow<Client> = repository.getClientById(clientId)
}