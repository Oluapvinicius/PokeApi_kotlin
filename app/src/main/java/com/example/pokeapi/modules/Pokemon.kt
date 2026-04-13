package com.example.pokeapi.modules

import androidx.compose.ui.graphics.Color

enum class PokemonType(val color: Color) {
    GRASS(Color(0xFF74CB48)),
    FIRE(Color(0xFFF57D31)),
    WATER(Color(0xFF6493EB)),
    BUG(Color(0xFFA7B723)),
    NORMAL(Color(0xFFAAA67F)),
    POISON(Color(0xFFA43E9E)),
    ELECTRIC(Color(0xFFF9CF30)),
    GHOST(Color(0xFF70559B)),
    PSYCHIC(Color(0xFFFB5584)),
    STEEL(Color(0xFFB7B9D0));

    companion object {
        fun fromString(type: String) = entries.find { it.name.equals(type, true) } ?: NORMAL
    }
}

data class PokemonListResponse(val results: List<PokemonNamedResource>)
data class PokemonNamedResource(val name: String, val url: String)

data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val type: String
)