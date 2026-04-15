package com.example.pokeapi.PokeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokeapi.R
import com.example.pokeapi.modules.PokemonType

@Composable
fun DetaisDoPokemon(pokemonId: Int, onBack: () -> Unit) {

    val vm: PokeViewModel.DetailViewModel = viewModel()

    LaunchedEffect(pokemonId) {
        vm.getPokemonDetails(pokemonId)
    }

    val pokemon = vm.pokemonDetails


    if (pokemon == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Color.Red)
        }
        return
    }


    val mainColor = PokemonType.fromString(pokemon.types[0].type.name).color

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(mainColor)
    ) {

        Box(modifier = Modifier.fillMaxWidth().height(200.dp)) {
            Image(
                painter = painterResource(R.drawable.pokeball),
                contentDescription = "",
                modifier = Modifier
                    .size(200.dp)
                    .offset(x = 20.dp, y = 20.dp)
                    .align(Alignment.TopEnd)
                    .alpha(0.1f),
                colorFilter = ColorFilter.tint(Color.White)
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 40.dp, start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color.White)
                }
                Text(
                    text = pokemon.name.replaceFirstChar { it.uppercase() },
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "#${pokemon.id.toString().padStart(3, '0')}",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }


        Card(
            modifier = Modifier.fillMaxSize().padding(8.dp),
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {

                Text(
                    text = "Base Stats",
                    color = mainColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )


                pokemon.stats.forEach { statSlot ->
                    val statNameLabel = when(statSlot.stat.name) {
                        "hp" -> "HP"
                        "attack" -> "ATK"
                        "defense" -> "DEF"
                        "special-attack" -> "SATK"
                        "special-defense" -> "SDEF"
                        "speed" -> "SPD"
                        else -> statSlot.stat.name.uppercase()
                    }

                    StatRow(
                        label = statNameLabel,
                        value = statSlot.baseValue,
                        color = mainColor
                    )
                }
            }
        }
    }
}

@Composable
fun StatRow(label: String, value: Int, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, modifier = Modifier.weight(0.2f), fontWeight = FontWeight.Bold, color = color)
        Text(text = value.toString().padStart(3, '0'), modifier = Modifier.weight(0.15f))

        LinearProgressIndicator(
            progress = { value / 255f },
            modifier = Modifier.weight(0.65f).height(8.dp).clip(CircleShape),
            color = color,
            trackColor = color.copy(alpha = 0.2f)
        )
    }
}