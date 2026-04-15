package com.example.pokeapi.PokeScreen

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapi.modules.*
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import java.net.URL

class PokeViewModel : ViewModel() {
    var pokemonList by mutableStateOf<List<Pokemon>>(emptyList())
    var searchQuery by mutableStateOf("")

    init {
        fetchPokemons()
    }

    private fun fetchPokemons() {
        viewModelScope.launch {
            try {

                val response = PokeApiService.RetrofitInstance.api.getPokemonList()


                val detailedPokemonList = response.results.map { res ->
                    async {

                        PokeApiService.RetrofitInstance.api.getPokemonDetails(res.name)
                    }
                }.awaitAll()


                pokemonList = detailedPokemonList.map { detail ->
                    Pokemon(
                        id = detail.id,
                        name = detail.name.replaceFirstChar { it.uppercase() },
                        imageUrl = detail.sprites.other.officialArtwork.frontDefault,
                        type = detail.types.firstOrNull()?.type?.name ?: ""
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getFilteredList(): List<Pokemon> {
        return if (searchQuery.isEmpty()) pokemonList
        else pokemonList.filter {
            it.name.contains(searchQuery, ignoreCase = true) || it.id.toString() == searchQuery
        }
    }
}