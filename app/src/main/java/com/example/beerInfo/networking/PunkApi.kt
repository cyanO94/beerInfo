package com.example.beerInfo.networking

import com.example.beerInfo.data.Beer
import retrofit2.http.GET
import retrofit2.http.Query

interface PunkApi {
    @GET("beers")
    suspend fun getBeers(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int,
        @Query("beer_name") beerName: String
    ) : List<Beer>
}