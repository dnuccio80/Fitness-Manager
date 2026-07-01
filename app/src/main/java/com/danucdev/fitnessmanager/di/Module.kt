package com.danucdev.fitnessmanager.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.danucdev.fitnessmanager.data.impl.ClientRepositoryImpl
import com.danucdev.fitnessmanager.data.dao.ClientDao
import com.danucdev.fitnessmanager.data.dao.ProductServiceDao
import com.danucdev.fitnessmanager.data.dao.TransactionDao
import com.danucdev.fitnessmanager.data.db.AppDatabase
import com.danucdev.fitnessmanager.data.impl.ProductServiceRepositoryImpl
import com.danucdev.fitnessmanager.data.impl.TransactionRepositoryImpl
import com.danucdev.fitnessmanager.domain.repositories.ClientRepository
import com.danucdev.fitnessmanager.domain.repositories.ProductServiceRepository
import com.danucdev.fitnessmanager.domain.repositories.TransactionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_db"
        ) .addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                db.execSQL(
                    """
                INSERT INTO ProductServiceEntity (label, amount)
                VALUES ('Cuota mensual', 15000)
                """.trimIndent()
                )
            }
        })

            .build()
    }

    @Provides
    @Singleton
    fun provideClientDao(appDatabase: AppDatabase): ClientDao {
        return appDatabase.clientDao
    }

    @Provides
    @Singleton
    fun provideClientRepository(clientRepositoryImpl: ClientRepositoryImpl): ClientRepository {
        return clientRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideTransactionDao(appDatabase: AppDatabase): TransactionDao {
        return appDatabase.transactionDao
    }

    @Provides
    @Singleton
    fun provideTransactionRepository(transactionRepositoryImpl: TransactionRepositoryImpl): TransactionRepository {
        return transactionRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideProductServiceDao(appDatabase: AppDatabase): ProductServiceDao {
        return appDatabase.productServiceDao
    }

    @Provides
    @Singleton
    fun provideProductServicesRepository(productServiceRepositoryImpl: ProductServiceRepositoryImpl): ProductServiceRepository {
        return productServiceRepositoryImpl
    }

}