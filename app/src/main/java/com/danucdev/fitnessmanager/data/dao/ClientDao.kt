package com.danucdev.fitnessmanager.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.danucdev.fitnessmanager.data.entities.ClientEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClientDao {
    @Query("SELECT * FROM ClientEntity")
    fun getAllClients(): Flow<List<ClientEntity>>
}