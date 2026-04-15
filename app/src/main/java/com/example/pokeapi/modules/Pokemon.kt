package com.example.pokeapi.modules

import androidx.compose.ui.graphics.Color
import com.google.gson.annotations.SerializedName

enum class PokemonType(val color: Color) {
    GRASS(Color(0xFF74CB48)),
    FIRE(Color(0xFFF57D31)),
    WATER(Color(0xFF6493EB)),
    BUG(Color(0xFFA7B723)),
    FAIRY(Color(0xFFA00CFF)),
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


data class PokemonNamedResource(val name: String, val url: String)

data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val type: String
)



data class TypeInfo(
    val name: String,
    val url: String
)


data class PokemonListResponse(
    val results: List<PokemonBasic>
)

data class PokemonBasic(
    val name: String,
    val url: String
)

data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    val weight: Int,
    val height: Int,
    val stats: List<StatSlot>,
    val types: List<TypeSlot>,
    val sprites: Sprites
)

data class StatSlot(
    @SerializedName("base_stat") val baseValue: Int,
    val stat: StatInfo
)

data class StatInfo(
    val name: String
)

data class TypeSlot(
    val type: TypeInfo
)


data class Sprites(
    @SerializedName("other") val other: OtherSprites
)

data class OtherSprites(
    @SerializedName("official-artwork") val officialArtwork: OfficialArtwork
)

data class OfficialArtwork(
    @SerializedName("front_default") val frontDefault: String
)