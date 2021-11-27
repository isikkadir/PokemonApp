package com.isikkadir.pokemonapp.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.isikkadir.pokemonapp.R
import com.isikkadir.pokemonapp.databinding.FragmentMainPageBinding
import com.isikkadir.pokemonapp.databinding.FragmentPokemonDetailsBinding
import com.isikkadir.pokemonapp.viewmodel.DetailsPageViewModel
import com.isikkadir.pokemonapp.viewmodel.MainPageViewModel
import javax.inject.Inject

class PokemonDetails @Inject constructor(
    private val glide: RequestManager,
) : Fragment(R.layout.fragment_pokemon_details) {
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
            binding!!.detailsName.text = it.name
            if (it.abilities[0] != null) {
                binding!!.firstAbility.text = it.abilities[0].ability.name
            }
            if (it.abilities[1] != null) {
                binding!!.secondAbility.text = it.abilities[1].ability.name
            }
            binding!!.weightText.text = it.weight.toString()
            binding!!.heightText.text = it.height.toString()
            binding!!.progressHp.progress = it.stats[0].baseStat
            binding!!.progressAtk.progress = it.stats[1].baseStat
            binding!!.progressDef.progress = it.stats[2].baseStat
            binding!!.progressSpAtk.progress = it.stats[3].baseStat
            binding!!.progressSpDef.progress = it.stats[4].baseStat
            binding!!.progressSpd.progress = it.stats[5].baseStat
            binding!!.detailsImageView.background = null
            val url =
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${pokeNumber}.png"
            glide.load(url).into(binding!!.detailsImageView);
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