package com.isikkadir.pokemonapp.api

import com.isikkadir.pokemonapp.model.pokemonlist.PokemonList
import com.plcoding.jetpackcomposepokedex.data.remote.responses.Pokemon
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonList

    @GET("pokemon/{name}")
    suspend fun getPokemonDetails(
        @Path("name") name: String,
    ): Pokemon
}