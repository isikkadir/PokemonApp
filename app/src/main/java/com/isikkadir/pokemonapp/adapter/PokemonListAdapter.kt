package com.isikkadir.pokemonapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.isikkadir.pokemonapp.R
import com.isikkadir.pokemonapp.model.PokedexListEntry
import com.isikkadir.pokemonapp.model.pokemonlist.Result
import javax.inject.Inject

class PokemonListAdapter @Inject constructor(
    val glide: RequestManager
) : RecyclerView.Adapter<PokemonListAdapter.PokemonViewHolder>() {

    class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val diffUtil = object : DiffUtil.ItemCallback<PokedexListEntry>() {
        override fun areItemsTheSame(
            oldItem: PokedexListEntry,
            newItem: PokedexListEntry
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: PokedexListEntry,
            newItem: PokedexListEntry
        ): Boolean {
            return oldItem == newItem
        }


    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var pokemonList: List<PokedexListEntry>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemonName = holder.itemView.findViewById<TextView>(R.id.item_pokemon_name)
        val pokemonImage = holder.itemView.findViewById<ImageView>(R.id.item_pokemon_image)
        val pokemon = pokemonList[position]
        holder.itemView.apply {
            pokemonName.text = "Name: ${pokemon.pokemonName}"
        }
    }

    override fun getItemCount(): Int {
        return pokemonList.count()
    }

}