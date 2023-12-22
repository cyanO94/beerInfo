package com.example.beerInfo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.beerInfo.R
import com.example.beerInfo.databinding.FragmentBeerSearchBinding
import com.example.beerInfo.data.Beer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchBeerFragment : Fragment() {
    private lateinit var binding: FragmentBeerSearchBinding
    private val viewModel: BeerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBeerSearchBinding.inflate(inflater, container, false)

        val listener = object : BeersAdapter.OnBeerClickListener {
            override fun onBeerClick(beer: Beer) {
                val action = SearchBeerFragmentDirections
                    .actionSearchBeerFragmentToBeerDetailFragment(beer)
                findNavController().navigate(action)
            }
        }
        val adapter = BeersAdapter(listener)
        binding.rvResultList.adapter = adapter

        binding.btSearch.setOnClickListener {
            val searchQuery = binding.etSearch.text.trim().toString()

            if (searchQuery.isEmpty()) {
                Toast.makeText(context, getString(R.string.search_no_text), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            viewModel.fetchData(searchQuery)
        }

        viewModel.beersLivedata.observe(requireActivity()) {
            adapter.submitData(lifecycle, it)
            binding.rvResultList.scrollToPosition(0)
        }

        adapter.addLoadStateListener {
            if (it.refresh is LoadState.NotLoading && adapter.itemCount == 0) {
                Toast.makeText(context, getString(R.string.search_no_result), Toast.LENGTH_LONG).show()
            }

            if (it.refresh is LoadState.Error) {
                Toast.makeText(context, getString(R.string.network_err_msg), Toast.LENGTH_LONG).show()
            }
        }

        return binding.root
    }

}
