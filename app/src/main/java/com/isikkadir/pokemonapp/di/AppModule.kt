package com.isikkadir.pokemonapp.di

import com.isikkadir.pokemonapp.api.PokemonApi
import com.isikkadir.pokemonapp.repository.MainPageRepository
import com.isikkadir.pokemonapp.repository.MainRepositoryInterface
import com.isikkadir.pokemonapp.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRetrofit(): PokemonApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PokemonApi::class.java)
    }

    @Singleton
    @Provides
    fun injectMainPageRepository(api: PokemonApi) =
        MainPageRepository(api) as MainRepositoryInterface

}