package com.example.beerInfo.ui

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.beerInfo.databinding.FragmentBeerDetailBinding
import com.example.beerInfo.R
import com.example.beerInfo.data.Beer

class BeerDetailFragment : Fragment() {
    private lateinit var binding: FragmentBeerDetailBinding
    private var beer:Beer? = null
    private val viewModel: BeerViewModel by activityViewModels()

    private fun setUI() {
        beer?.let {beer->
            Glide.with(binding.ivBeer.context)
                .load(beer.image_url)
                .into(binding.ivBeer)
            binding.tvBeerTitle.text = beer.name
            binding.tvBeerDescription.text = beer.description
            binding.tvBeerTagline.text = beer.tagline

            binding.ivBookMark.setImageResource(
                if (beer.isBookmarked)  R.drawable.bookmark_marked
                else R.drawable.bookmark_unmarked
            )

            binding.ivBookMark.setOnClickListener {
                beer.isBookmarked = !beer.isBookmarked
                binding.ivBookMark.setImageResource(
                    if (beer.isBookmarked)  R.drawable.bookmark_marked
                    else R.drawable.bookmark_unmarked
                )

                viewModel.updateBeer(beer)
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBeerDetailBinding.inflate(inflater, container, false)

        beer = if (Build.VERSION.SDK_INT >= 33)
            arguments?.getParcelable("beer", Beer::class.java)
        else
            arguments?.getParcelable("beer")

        setUI()

        return binding.root
    }
}