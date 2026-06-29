package com.danucdev.fitnessmanager.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.danucdev.fitnessmanager.data.dao.ClientDao
import com.danucdev.fitnessmanager.data.dao.ProductServiceDao
import com.danucdev.fitnessmanager.data.dao.TransactionDao
import com.danucdev.fitnessmanager.data.entities.ClientEntity
import com.danucdev.fitnessmanager.data.entities.ProductServiceEntity
import com.danucdev.fitnessmanager.data.entities.TransactionEntity

@Database(
    entities = [ClientEntity::class, TransactionEntity::class, ProductServiceEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase: RoomDatabase() {
    abstract val clientDao: ClientDao
    abstract val transactionDao: TransactionDao
    abstract val productServiceDao: ProductServiceDao
}