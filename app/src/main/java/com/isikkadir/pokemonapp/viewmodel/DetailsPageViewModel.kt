package com.isikkadir.pokemonapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isikkadir.pokemonapp.model.PokedexListEntry
import com.isikkadir.pokemonapp.repository.DetailsRepositoryInterface
import com.isikkadir.pokemonapp.utils.Constants
import com.isikkadir.pokemonapp.utils.Resource
import com.plcoding.jetpackcomposepokedex.data.remote.responses.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DetailsPageViewModel @Inject constructor(
    private val repo: DetailsRepositoryInterface,
) : ViewModel() {

    private var pokemon = MutableLiveData<Pokemon>()
    val pokemonPublic: LiveData<Pokemon> get() = pokemon

    private var isLoading = MutableLiveData<Boolean>()
    val isLoadingPublic: LiveData<Boolean> get() = isLoading

    private var loadError = MutableLiveData<String>()
    val loadErrorPublic: LiveData<String> get() = loadError



    fun loadPokemonDetails(number : Int) {
        isLoading.value = true
        viewModelScope.launch {
            val result = repo.getPokemonDetails(number)
            when (result) {
                is Resource.Success -> {
                    pokemon.value = result.data!!
                    loadError.value = ""
                    isLoading.value = false
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
            }
        }
    }

}