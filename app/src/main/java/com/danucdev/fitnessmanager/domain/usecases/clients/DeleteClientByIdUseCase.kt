package com.danucdev.fitnessmanager.domain.usecases.clients

import com.danucdev.fitnessmanager.domain.repositories.ClientRepository
import javax.inject.Inject

class DeleteClientByIdUseCase @Inject constructor(private val clientRepository: ClientRepository) {

    suspend operator fun invoke(clientId:Int) = clientRepository.deleteClientById(clientId)

}