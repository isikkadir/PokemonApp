package com.isikkadir.pokemonapp.repository

import com.isikkadir.pokemonapp.api.PokemonApi
import com.isikkadir.pokemonapp.utils.Resource
import com.plcoding.jetpackcomposepokedex.data.remote.responses.Pokemon
import javax.inject.Inject

class DetailsPageRepository @Inject constructor(
    private val api: PokemonApi,
) : DetailsRepositoryInterface {
    override suspend fun getPokemonDetails(number: Int): Resource<Pokemon> {
        val response = try {
            api.getPokemonDetails(number)
        } catch (e: Exception) {
            return Resource.Error("Exception")
        }
        return Resource.Success(response)
    }
}

