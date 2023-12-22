package com.example.beerInfo.di

import android.app.Application
import androidx.room.Room
import com.example.beerInfo.database.BeerDao
import com.example.beerInfo.database.BeerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideBeerDatabase(application: Application): BeerDatabase {
        return Room.databaseBuilder(
            application,
            BeerDatabase::class.java,
            "beer_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideBeerDao(beerDatabase: BeerDatabase): BeerDao {
        return beerDatabase.beerDao()
    }
}