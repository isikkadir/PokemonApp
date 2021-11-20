package com.isikkadir.pokemonapp.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.isikkadir.pokemonapp.R
import com.isikkadir.pokemonapp.adapter.PokemonListAdapter
import com.isikkadir.pokemonapp.databinding.FragmentMainPageBinding
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
        binding!!.recyclerView.layoutManager = LinearLayoutManager(requireContext())

    }

    private fun subscribeToObservers() {
        viewModel.pokemonListPublic.observe(viewLifecycleOwner, {
            adapter.pokemonList = it
        })
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}