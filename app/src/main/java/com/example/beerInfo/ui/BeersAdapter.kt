package com.example.beerInfo.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beerInfo.R
import com.example.beerInfo.databinding.BeerSimpleItemBinding
import com.example.beerInfo.data.Beer

class BeersAdapter(private val onClickListener: OnBeerClickListener)
    : PagingDataAdapter<Beer, RecyclerView.ViewHolder>(BeerComparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = BeerSimpleItemBinding.inflate(inflater, parent, false)
        return BeersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val beer = getItem(position)
        beer?.let {
            (holder as BeersViewHolder).bind(beer, onClickListener)
        }
    }

    class BeersViewHolder(
        private val binding: BeerSimpleItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(beer: Beer?, onClickListener: OnBeerClickListener) {
            beer?.let {
                binding.beerName.text = it.name
                Glide.with(binding.ivBeer.context)
                    .load(it.image_url)
                    .into(binding.ivBeer)

                binding.cvBeer.setOnClickListener {_->
                    onClickListener.onBeerClick(it)
                }

                binding.ivBookMark.setImageResource(
                    if (it.isBookmarked) R.drawable.bookmark_marked
                    else R.drawable.bookmark_unmarked
                )
            }
        }
    }

    interface OnBeerClickListener{
        fun onBeerClick(beer: Beer)
    }

    companion object {
        private val BeerComparator  = object : DiffUtil.ItemCallback<Beer>() {
            override fun areItemsTheSame(old: Beer, new: Beer): Boolean =
                old.id == new.id

            override fun areContentsTheSame(old: Beer, new: Beer): Boolean =
                old == new
        }
    }
}