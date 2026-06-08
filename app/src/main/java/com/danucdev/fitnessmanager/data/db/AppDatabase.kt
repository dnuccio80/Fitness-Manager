package com.danucdev.fitnessmanager.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.danucdev.fitnessmanager.data.dao.ClientDao
import com.danucdev.fitnessmanager.data.entities.ClientEntity

@Database(
    entities = [ClientEntity::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract val clientDao: ClientDao
}