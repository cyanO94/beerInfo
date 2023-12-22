package com.example.beerInfo.di

import com.example.beerInfo.networking.PunkApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PunkApiModule {
    @Singleton
    @Provides
    fun providePunkApi(): PunkApi {
        val BASE_URL = "https://api.punkapi.com/v2/"

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(PunkApi::class.java)
    }
}