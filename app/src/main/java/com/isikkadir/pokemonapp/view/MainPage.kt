package com.isikkadir.pokemonapp.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.isikkadir.pokemonapp.R
import com.isikkadir.pokemonapp.adapter.PokemonListAdapter
import com.isikkadir.pokemonapp.databinding.FragmentMainPageBinding
import com.isikkadir.pokemonapp.utils.EqualSpacingItemDecoration
import com.isikkadir.pokemonapp.viewmodel.MainPageViewModel
import javax.inject.Inject

class MainPage @Inject constructor(
    private val adapter: PokemonListAdapter,
) : Fragment(R.layout.fragment_main_page) {

    private var binding: FragmentMainPageBinding? = null
    lateinit var viewModel: MainPageViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainPageBinding.bind(view)
        viewModel = ViewModelProvider(requireActivity()).get(MainPageViewModel::class.java)

        subscribeToObservers()
        binding!!.recyclerView.adapter = adapter
        binding!!.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding!!.recyclerView.addItemDecoration(
            EqualSpacingItemDecoration(
                50,
                EqualSpacingItemDecoration.GRID
            )
        )
        binding!!.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManagerr = recyclerView.layoutManager as LinearLayoutManager
                val total = layoutManagerr!!.itemCount
                val currentLastItem: Int = layoutManagerr.findLastVisibleItemPosition()
                if (currentLastItem == total - 1) {
                    if (binding!!.searchText.text.isEmpty()) {
                        viewModel.loadPokemonPaginated()
                        subscribeToObservers()
                    }
                }
            }
        })
        binding!!.searchText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.e("Yazı: ", "Yazı = ${binding!!.searchText.text}")
                listenToSearchingPokemon(binding!!.searchText.text.toString())
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun listenToSearchingPokemon(query: String) {
        Log.e("Msg", "isSearching. Wait!!")
        viewModel.searchPokemon(query)
        viewModel.pokemonListPublic.observe(viewLifecycleOwner, { list ->
            adapter.pokemonList = list
        })
    }

    private fun subscribeToObservers() {
        viewModel.isLoadingPublic.observe(viewLifecycleOwner, {
            if (it == true) {
                binding!!.progressbar.visibility = View.VISIBLE
                binding!!.recyclerView.visibility = View.GONE
                Log.e("msg", "progress visible, recyclerview gone")
            } else {
                binding!!.progressbar.visibility = View.GONE
                binding!!.recyclerView.visibility = View.VISIBLE
                Log.e("msg", "progress gone, recyclerview visible")
            }
        })
        viewModel.pokemonListPublic.observe(viewLifecycleOwner, {
            adapter.pokemonList = it
        })
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}