package com.danucdev.fitnessmanager.di

import android.content.Context
import androidx.room.Room
import com.danucdev.fitnessmanager.FitnessApplication
import com.danucdev.fitnessmanager.data.ClientRepositoryImpl
import com.danucdev.fitnessmanager.data.dao.ClientDao
import com.danucdev.fitnessmanager.data.db.AppDatabase
import com.danucdev.fitnessmanager.domain.ClientRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
        )
            .fallbackToDestructiveMigration(false)
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

}