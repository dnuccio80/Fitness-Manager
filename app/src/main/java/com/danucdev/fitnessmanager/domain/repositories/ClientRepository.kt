package com.danucdev.fitnessmanager.domain.repositories

import com.danucdev.fitnessmanager.domain.models.Client
import kotlinx.coroutines.flow.Flow

interface ClientRepository {
    fun getAllClients(): Flow<List<Client>>
    fun getClientById(clientId:Int): Flow<Client>
    suspend fun addClient(client: Client)
    suspend fun deleteClientById(clientId:Int)
}