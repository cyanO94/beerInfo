package com.example.beerInfo.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.beerInfo.database.BeerDao
import com.example.beerInfo.networking.PunkApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BeerPagingSource(
    private val pageSize: Int = 10,
    private val query: String,
    private val beerDao: BeerDao,
    private val punkApi: PunkApi
) : PagingSource<Int, Beer>()
{
    override fun getRefreshKey(state: PagingState<Int, Beer>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Beer> {
        try {
            val nextPageNumber = params.key ?: 1
            val beers = punkApi.getBeers(nextPageNumber, pageSize, query)
            val prevKey = if (nextPageNumber <= 1) null
                            else nextPageNumber - 1
            val nextKey = if(beers.size < pageSize) null
                            else nextPageNumber + 1

            val localBeers = updateBeersWithBookmarkState(beers)
            return LoadResult.Page(
                data = localBeers,
                prevKey = prevKey,
                nextKey = nextKey
            )
        }
        catch (e: Exception) {
            e.printStackTrace()
            return LoadResult.Error(e)
        }
    }

    private suspend fun updateBeersWithBookmarkState(beers: List<Beer>) : List<Beer> =
        withContext(Dispatchers.IO) {
            beerDao.insertBeers(beers)

            return@withContext beers.map {
                it.copy(isBookmarked = beerDao.getBeerById(it.id)?.isBookmarked ?: false)
            }
        }
}