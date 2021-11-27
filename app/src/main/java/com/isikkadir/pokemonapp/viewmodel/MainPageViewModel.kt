package com.isikkadir.pokemonapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isikkadir.pokemonapp.model.PokedexListEntry
import com.isikkadir.pokemonapp.repository.MainRepositoryInterface
import com.isikkadir.pokemonapp.utils.Constants.PAGE_SIZE
import com.isikkadir.pokemonapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor(
    private val repo: MainRepositoryInterface,
) : ViewModel() {

    private var curPage = 0

    private var pokemonList = MutableLiveData<List<PokedexListEntry>>(listOf())
    val pokemonListPublic: LiveData<List<PokedexListEntry>> get() = pokemonList

    private var isLoading = MutableLiveData<Boolean>()
    val isLoadingPublic: LiveData<Boolean> get() = isLoading

    private var endReached = MutableLiveData<Boolean>()
    val endReachedPublic: LiveData<Boolean> get() = endReached

    private var loadError = MutableLiveData<String>()
    val loadErrorPublic: LiveData<String> get() = loadError


    private var cachedPokemonList = listOf<PokedexListEntry>()
    private var isSearchStarting = true
    var isSearching = MutableLiveData(false)

    init {
        loadPokemonPaginated()
    }

    fun loadPokemonPaginated() {
        isLoading.value = true
        viewModelScope.launch {
            val result = repo.getPokemonList(PAGE_SIZE, curPage * PAGE_SIZE)
            when (result) {
                is Resource.Success -> {
                    endReached.value = curPage * PAGE_SIZE >= result.data!!.count
                    val pokedexEntries = result.data.results.mapIndexed { index, entry ->
                        val number = if (entry.url.endsWith("/")) {
                            entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                        } else {
                            entry.url.takeLastWhile { it.isDigit() }
                        }
                        val url =
                            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
                        PokedexListEntry(entry.name.capitalize(Locale.ROOT), url, number.toInt())
                    }
                    curPage++

                    loadError.value = ""
                    isLoading.value = false
                    pokemonList.value = pokemonList.value?.plus(pokedexEntries)
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
            }
        }

    }

    fun searchPokemon(query: String) {
        val listToSearch = if (isSearchStarting) {
            pokemonList.value
        } else {
            cachedPokemonList
        }
        viewModelScope.launch {
            if (query.isEmpty()) {
                pokemonList.value = cachedPokemonList
                isSearching.value = false
                isSearchStarting = true
                return@launch
            }
            val results = listToSearch?.filter {
                it.pokemonName.contains(
                    query.trim(),
                    ignoreCase = true
                ) || it.number.toString() == query.trim()
            }
            if (isSearchStarting) {
                cachedPokemonList = pokemonList.value!!
                isSearchStarting = false
            }
            pokemonList.value = results!!
            Log.e("Result", "Result Size ${results!!.size}")
            isSearching.value = true
        }
    }
}


