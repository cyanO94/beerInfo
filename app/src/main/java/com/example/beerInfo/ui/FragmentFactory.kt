package com.example.beerInfo.ui

import androidx.fragment.app.FragmentFactory

class FragmentFactory : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String) = when(className) {
        SearchBeerFragment::class.java.name -> SearchBeerFragment()
        BeerDetailFragment::class.java.name -> BeerDetailFragment()
        else -> super.instantiate(classLoader, className)
    }
}