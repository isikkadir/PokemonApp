package com.isikkadir.pokemonapp.viewmodel

import androidx.lifecycle.ViewModel
import com.isikkadir.pokemonapp.repository.DetailsRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsPageViewModel @Inject constructor(
    private val repo: DetailsRepositoryInterface,
) : ViewModel() {

}