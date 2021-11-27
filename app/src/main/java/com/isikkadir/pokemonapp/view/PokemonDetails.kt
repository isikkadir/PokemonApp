package com.isikkadir.pokemonapp.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.isikkadir.pokemonapp.R
import com.isikkadir.pokemonapp.databinding.FragmentMainPageBinding
import com.isikkadir.pokemonapp.databinding.FragmentPokemonDetailsBinding
import com.isikkadir.pokemonapp.viewmodel.DetailsPageViewModel
import com.isikkadir.pokemonapp.viewmodel.MainPageViewModel
import javax.inject.Inject

class PokemonDetails : Fragment(R.layout.fragment_pokemon_details) {
    private var binding: FragmentPokemonDetailsBinding? = null
    lateinit var viewModel: DetailsPageViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPokemonDetailsBinding.bind(view)
        viewModel = ViewModelProvider(requireActivity()).get(DetailsPageViewModel::class.java)

        val args: PokemonDetailsArgs by navArgs()
        getPokemonDetails(args.pokeNumber)
        subscribeToObservers(args.pokeNumber)
    }

    private fun subscribeToObservers(pokeNumber: Int) {
        viewModel.isLoadingPublic.observe(viewLifecycleOwner, {
            if (it == true) {
                binding!!.progressBarDetails.visibility = View.VISIBLE
                binding!!.generalLayout.visibility = View.GONE
            } else {
                binding!!.progressBarDetails.visibility = View.GONE
                binding!!.generalLayout.visibility = View.VISIBLE
            }
        })

        viewModel.pokemonPublic.observe(viewLifecycleOwner, {
            binding!!.detailsNumber.text = pokeNumber.toString()
        })
    }

    private fun getPokemonDetails(pokeNumber: Int) {
        viewModel.loadPokemonDetails(pokeNumber)
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}