package com.isikkadir.pokemonapp.repository

import com.isikkadir.pokemonapp.utils.Resource
import com.plcoding.jetpackcomposepokedex.data.remote.responses.Pokemon


interface DetailsRepositoryInterface {
    suspend fun getPokemonDetails(number: Int): Resource<Pokemon>
}