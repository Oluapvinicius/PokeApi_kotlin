package com.example.pokeapi.PokeScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.pokeapi.components.Logo
import com.example.pokeapi.modules.Pokemon
import com.example.pokeapi.modules.PokemonType

@Composable
fun HomeScreen(vm: PokeViewModel = viewModel(), onPokemonClick: (Int) -> Unit) {
    val list = vm.getFilteredList()

    Column(modifier = Modifier.fillMaxSize()) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFDC0A2D))
                .height(80.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Logo()

        }


        OutlinedTextField(
            value = vm.searchQuery,
            onValueChange = { vm.searchQuery = it },
            placeholder = { Text("Nome ou ID", fontSize = 14.sp) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.LightGray,
                unfocusedBorderColor = Color.LightGray
            )
        )


        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(list) { pokemon ->

                PokemonCard(
                    pokemon = pokemon,
                    onPokemonClick = onPokemonClick
                )
            }

        }
    }
}
@Composable
fun PokemonCard(
    pokemon: com.example.pokeapi.modules.Pokemon,
    onPokemonClick: (Int) -> Unit
) {

    val colorType = PokemonType.fromString(pokemon.type).color

    OutlinedCard(
        onClick = { onPokemonClick(pokemon.id) },
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, colorType),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "#${pokemon.id.toString().padStart(3, '0')}",
                modifier = Modifier
                    .padding(4.dp)
                    .align(Alignment.End),
                fontSize = 10.sp,
                color = colorType,
                fontWeight = FontWeight.Bold
            )

            AsyncImage(
                model = pokemon.imageUrl,
                contentDescription = pokemon.name,
                modifier = Modifier
                    .size(80.dp)
                    .padding(4.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorType)
                    .padding(vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = pokemon.name,
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}