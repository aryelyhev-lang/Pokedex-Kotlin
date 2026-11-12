
package com.example.listadeexercicios06

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.listadeexercicios06.screen.PokedexScreen
import com.example.listadeexercicios06.screen.PokemonDetalhesScreen
import com.example.listadeexercicios06.screen.PokemonViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            val pokemonViewModel: PokemonViewModel = viewModel()

            var pokemonSelecionado by remember {
                mutableStateOf<Int?>(null)
            }

            if (pokemonSelecionado == null) {
                PokedexScreen(
                    pokemonViewModel = pokemonViewModel,
                    onPokemonClick = { id ->
                        pokemonSelecionado = id
                    }
                )
            } else {
                PokemonDetalhesScreen(
                    pokemonId = pokemonSelecionado!!,
                    pokemonViewModel = pokemonViewModel,
                    onVoltar = {
                        pokemonSelecionado = null
                    }
                )
            }
        }
    }
}
