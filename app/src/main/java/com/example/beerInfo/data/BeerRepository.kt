package com.example.beerInfo.data

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.beerInfo.database.BeerDao
import com.example.beerInfo.networking.PunkApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class BeerRepository @Inject constructor(
    private val punkApi: PunkApi,
    private val beerDao: BeerDao,
) {
    companion object {
        private const val PAGE_SIZE = 10
    }

    fun getBeerPagingFlow(query: String): Flow<PagingData<Beer>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = { BeerPagingSource(PAGE_SIZE, query, beerDao, punkApi) }
        ).flow
    }

    suspend fun updateBeer(beer: Beer) {
        beerDao.updateBeer(beer)
    }
}