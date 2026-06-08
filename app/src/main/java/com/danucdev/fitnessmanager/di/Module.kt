package com.danucdev.fitnessmanager.di

import androidx.room.Room
import com.danucdev.fitnessmanager.Application
import com.danucdev.fitnessmanager.data.dao.ClientDao
import com.danucdev.fitnessmanager.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "app_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideClientDao(appDatabase: AppDatabase): ClientDao {
        return appDatabase.clientDao
    }

}