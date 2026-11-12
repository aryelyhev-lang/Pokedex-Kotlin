
package com.example.listadeexercicios06

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.listadeexercicios06.screen.pokedex.PokedexScreen
import com.example.listadeexercicios06.screen.PokemonDetalhesScreen
import com.example.listadeexercicios06.screen.pokedex.PokedexViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            val pokedexViewModel: PokedexViewModel = ViewModel()

            var pokemonSelecionado by remember {
                mutableStateOf<Int?>(null)
            }

            if (pokemonSelecionado == null) {
                PokedexScreen(
                    pokedexViewModel = pokedexViewModel,
                    onPokemonClick = { id ->
                        pokemonSelecionado = id
                    }
                )
            } else {
                PokemonDetalhesScreen(
                    pokemonId = pokemonSelecionado!!,
                    pokedexViewModel = pokedexViewModel,
                    onVoltar = {
                        pokemonSelecionado = null
                    }
                )
            }
        }
    }
}
