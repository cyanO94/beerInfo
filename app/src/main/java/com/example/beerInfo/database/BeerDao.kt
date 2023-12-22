package com.example.beerInfo.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.beerInfo.data.Beer

@Dao
interface BeerDao {
    @Query("SELECT * FROM beer_table WHERE id = :beerId")
    fun getBeerById(beerId: Int): Beer?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBeers(beer: List<Beer>)

    @Update
    suspend fun updateBeer(beer: Beer)

}