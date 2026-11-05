package com.example.listadeexercicios06.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Arquivo responsavel por: Responsável pela interface visual do aplicativo

// Modelo temporário para os cards da tela
private data class PokemonCard(
    val id: Int,
    val nome: String,
    val emoji: String,
    val cor: Color
)

// Dados temporários para visualizar a interface
private val pokemonsExemplo = listOf(
    PokemonCard(1, "Bulbasaur", "🌱", Color(0xFF8BC65A)),
    PokemonCard(4, "Charmander", "🔥", Color(0xFFE98B45)),
    PokemonCard(7, "Squirtle", "🐢", Color(0xFF7195DF)),
    PokemonCard(12, "Butterfree", "🦋", Color(0xFFA5B63E)),
    PokemonCard(25, "Pikachu", "⚡", Color(0xFFF0CD4C)),
    PokemonCard(92, "Gastly", "👻", Color(0xFF735A9C)),
    PokemonCard(132, "Ditto", "🟣", Color(0xFFAAA78A)),
    PokemonCard(151, "Mew", "🩷", Color(0xFFE65C88)),
    PokemonCard(304, "Aron", "⚙️", Color(0xFFB0B2C6))
)

@Composable
fun PokedexScreen() {

    var pesquisa by remember { mutableStateOf("") }

    val pokemonsFiltrados = pokemonsExemplo.filter {
        it.nome.contains(pesquisa, ignoreCase = true) ||
                it.id.toString() == pesquisa.trim().removePrefix("#")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {

        // CABEÇALHO
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .background(Color(0xFFE53935))
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "◉",
                color = Color.White,
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.width(20.dp))

            Text(
                text = "Pokédex",
                color = Color.White,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // CAMPO DE PESQUISA
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 30.dp)
                .height(54.dp)
                .background(
                    Color.White,
                    RoundedCornerShape(16.dp)
                )
                .border(
                    1.dp,
                    Color.LightGray,
                    RoundedCornerShape(16.dp)
                )
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(modifier = Modifier.weight(1f)) {

                if (pesquisa.isEmpty()) {
                    Text(
                        text = "Nome ou ID",
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }

                BasicTextField(
                    value = pesquisa,
                    onValueChange = { pesquisa = it },
                    singleLine = true,
                    textStyle = androidx.compose.ui.text.TextStyle(
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Text(
                text = "⌕",
                fontSize = 28.sp,
                color = Color.Gray
            )
        }

        // GRADE DE POKÉMON
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 20.dp)
        ) {

            items(pokemonsFiltrados) { pokemon ->

                PokemonCardItem(pokemon)
            }
        }
    }
}

// CARD INDIVIDUAL DO POKÉMON
@Composable
private fun PokemonCardItem(pokemon: PokemonCard) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .border(
                width = 1.5.dp,
                color = pokemon.cor,
                shape = RoundedCornerShape(14.dp)
            ),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            // NÚMERO DO POKÉMON
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp, end = 8.dp),
                contentAlignment = Alignment.CenterEnd
            ) {

                Text(
                    text = "#${pokemon.id.toString().padStart(3, '0')}",
                    color = pokemon.cor,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // ESPAÇO DA IMAGEM
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = pokemon.emoji,
                    fontSize = 48.sp
                )
            }

            // NOME DO POKÉMON
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp)
                    .background(pokemon.cor),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = pokemon.nome,
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1
                )
            }
        }
    }
}
