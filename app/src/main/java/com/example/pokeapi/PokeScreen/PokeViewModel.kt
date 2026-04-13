package com.example.pokeapi.PokeScreen

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapi.modules.*
import kotlinx.coroutines.launch

class PokeViewModel : ViewModel() {
    var pokemonList by mutableStateOf<List<Pokemon>>(emptyList())
    var searchQuery by mutableStateOf("")

    init {
        fetchPokemons()
    }

        private fun fetchPokemons() {
            viewModelScope.launch {
                try {
                    val response = RetrofitInstance.api.getPokemonList()
                    pokemonList = response.results.mapIndexed { index, res ->
                        val id = index + 1
                        Pokemon(
                            id = id,
                            name = res.name.replaceFirstChar { it.uppercase() },
                            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png",
                            type = "${PokemonType}"
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