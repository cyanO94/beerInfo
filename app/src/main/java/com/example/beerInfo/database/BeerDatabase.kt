package com.example.beerInfo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.beerInfo.data.Beer

@Database(entities = [Beer::class], version = 1)
abstract class BeerDatabase : RoomDatabase() {
    abstract fun beerDao(): BeerDao
}