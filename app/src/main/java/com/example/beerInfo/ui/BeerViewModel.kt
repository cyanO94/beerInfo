package com.example.beerInfo.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.beerInfo.database.BeerDao
import com.example.beerInfo.data.Beer
import com.example.beerInfo.data.BeerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeerViewModel @Inject constructor(
    private val beerRepository: BeerRepository,
) : ViewModel() {

    val beersLivedata = MutableLiveData<PagingData<Beer>>()

    fun fetchData(searchQuery: String) {
        viewModelScope.launch {
            beerRepository.getBeerPagingFlow(searchQuery)
                .cachedIn(viewModelScope)
                .collect {
                    beersLivedata.postValue(it)
                }
        }
    }

    fun updateBeer(beer: Beer) {
        viewModelScope.launch(Dispatchers.IO) {
            beerRepository.updateBeer(beer)
        }
    }
}