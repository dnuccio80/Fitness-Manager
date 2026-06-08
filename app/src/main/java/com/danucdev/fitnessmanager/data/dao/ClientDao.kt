package com.danucdev.fitnessmanager.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.danucdev.fitnessmanager.data.entities.ClientEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClientDao {
    @Query("SELECT * FROM ClientEntity")
    fun getAllClients(): Flow<List<ClientEntity>>

    @Query("SELECT * FROM ClientEntity WHERE clientId = :clientId")
    fun getClientById(clientId: Int):Flow<ClientEntity>

    @Insert
    suspend fun addClient(client: ClientEntity)

    @Query("DELETE FROM ClientEntity WHERE clientId = :clientId")
    suspend fun deleteClientById(clientId:Int)

}