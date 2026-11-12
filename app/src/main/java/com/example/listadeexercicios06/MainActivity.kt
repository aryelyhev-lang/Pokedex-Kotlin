
package com.example.listadeexercicios06

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.listadeexercicios06.screen.PokedexScreen
import com.example.listadeexercicios06.screen.PokemonViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            PokedexScreen(pokemonViewModel = PokemonViewModel())
        }
    }
}
