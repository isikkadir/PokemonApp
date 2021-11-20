package com.isikkadir.pokemonapp.repository

import com.isikkadir.pokemonapp.model.pokemonlist.PokemonList
import com.isikkadir.pokemonapp.utils.Resource

interface MainRepositoryInterface {
    suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonList>
}