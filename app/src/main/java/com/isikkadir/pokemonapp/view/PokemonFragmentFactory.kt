package com.isikkadir.pokemonapp.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.isikkadir.pokemonapp.adapter.PokemonListAdapter
import javax.inject.Inject

class PokemonFragmentFactory @Inject constructor(
    private val adapter: PokemonListAdapter,
    private val glide: RequestManager,
    ) : FragmentFactory() {


    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            MainPage::class.java.name -> MainPage(adapter)
            PokemonDetails::class.java.name -> PokemonDetails(glide)
            else -> super.instantiate(classLoader, className)
        }
    }
}