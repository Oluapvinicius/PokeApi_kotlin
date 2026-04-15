package com.example.pokeapi

import android.R.attr.type
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokeapi.PokeScreen.DetaisDoPokemon
import com.example.pokeapi.PokeScreen.HomeScreen
import com.example.pokeapi.ui.theme.PokeApiTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PokeApiTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "lista") {

                        composable(route = "lista") {

                            HomeScreen(onPokemonClick = { id ->
                                navController.navigate("detalhes/$id")
                            })
                        }


                        composable(
                            route = "detalhes/{pokemonId}",
                            arguments = listOf(navArgument("pokemonId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val id = backStackEntry.arguments?.getInt("pokemonId") ?: 0


                            DetaisDoPokemon(
                                pokemonId = id,
                                onBack = { navController.popBackStack() }
                            )
                        }

                    }
                }
            }

        }
    }
}



