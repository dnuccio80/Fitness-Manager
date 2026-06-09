package com.danucdev.fitnessmanager.data

import com.danucdev.fitnessmanager.data.dao.ClientDao
import com.danucdev.fitnessmanager.domain.models.Client
import com.danucdev.fitnessmanager.domain.repositories.ClientRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ClientRepositoryImpl @Inject constructor(private val clientDao: ClientDao): ClientRepository {
    override fun getAllClients(): Flow<List<Client>> {
        return clientDao.getAllClients().map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun getClientById(clientId: Int): Flow<Client> {
        return clientDao.getClientById(clientId).map { it.toDomain() }
    }

    override suspend fun addClient(client: Client) {
        clientDao.addClient(client.toEntity())
    }

    override suspend fun deleteClientById(clientId: Int) {
        clientDao.deleteClientById(clientId)
    }
}