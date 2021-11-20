package com.isikkadir.pokemonapp.repository

import com.isikkadir.pokemonapp.api.PokemonApi
import com.isikkadir.pokemonapp.model.pokemonlist.PokemonList
import com.isikkadir.pokemonapp.utils.Resource
import javax.inject.Inject

class MainPageRepository @Inject constructor(
    private val api: PokemonApi,
) : MainRepositoryInterface {
    override suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonList> {
        val response = try {
            api.getPokemonList(limit, offset)
        } catch (e: Exception) {
            return Resource.Error("Exception")
        }
        return Resource.Success(response)
    }


}