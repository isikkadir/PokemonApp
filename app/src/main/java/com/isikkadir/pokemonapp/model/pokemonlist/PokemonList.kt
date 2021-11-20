package com.isikkadir.pokemonapp.model.pokemonlist

data class PokemonList(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)