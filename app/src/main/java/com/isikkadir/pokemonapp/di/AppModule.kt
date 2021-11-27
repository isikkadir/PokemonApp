package com.isikkadir.pokemonapp.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.isikkadir.pokemonapp.R
import com.isikkadir.pokemonapp.api.PokemonApi
import com.isikkadir.pokemonapp.repository.DetailsPageRepository
import com.isikkadir.pokemonapp.repository.DetailsRepositoryInterface
import com.isikkadir.pokemonapp.repository.MainPageRepository
import com.isikkadir.pokemonapp.repository.MainRepositoryInterface
import com.isikkadir.pokemonapp.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Singleton
    @Provides
    fun injectDetailsPageRepository(api: PokemonApi) =
        DetailsPageRepository(api) as DetailsRepositoryInterface

    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) = Glide
        .with(context).setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
        )


}