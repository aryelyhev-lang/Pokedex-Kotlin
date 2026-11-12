package com.example.listadeexercicios06.screen.pokedex

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listadeexercicios06.model.Pokemon


@Composable
fun PokedexScreen(
    pokedexViewModel: PokedexViewModel,
    onPokemonClick: (Int) -> Unit
) {
    var pesquisa by remember { mutableStateOf("") }

    val listaPokemons by pokedexViewModel.listaPokemons.collectAsState()
    val pokemonEncontrado by pokedexViewModel.pokemonEncontrado.collectAsState()
    val carregando by pokedexViewModel.carregando.collectAsState()
    val mensagemErro by pokedexViewModel.mensagemErro.collectAsState()

    val teclado = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(247, 236, 255))
    ) {

        // CABEÇALHO
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .background(Color(183, 129, 227))
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
                    Color(252, 248, 255),
                    RoundedCornerShape(16.dp)
                )
                .border(
                    1.dp,
                    Color(183, 129, 227),
                    RoundedCornerShape(16.dp)
                )
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.weight(1f)) {

                if (pesquisa.isEmpty()) {
                    Text(
                        text = "Nome ou ID",
                        color = Color(183, 129, 227),
                        fontSize = 16.sp
                    )
                }

                BasicTextField(
                    value = pesquisa,
                    onValueChange = { pesquisa = it },
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            pokedexViewModel.buscarPokemon(pesquisa)
                            teclado?.hide()
                        }
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            IconButton(
                onClick = {
                    pokedexViewModel.buscarPokemon(pesquisa)
                    teclado?.hide()
                }
            ) {
                Text(
                    text = "⌕",
                    fontSize = 28.sp,
                    color = Color(183, 129, 227)
                )
            }
        }

        // RESULTADOS
        when {
            carregando -> {
                Text(
                    text = "Carregando Pokémon...",
                    modifier = Modifier.padding(20.dp),
                    color = Color(183, 129, 227)
                )
            }

            mensagemErro.isNotEmpty() -> {
                Text(
                    text = mensagemErro,
                    modifier = Modifier.padding(20.dp),
                    color = Color.Red,
                    fontSize = 16.sp
                )
            }

            pokemonEncontrado != null -> {
                Text(
                    text = "Resultado da pesquisa",
                    modifier = Modifier.padding(horizontal = 20.dp),
                    color = Color(183, 129, 227),
                    fontWeight = FontWeight.Bold
                )

                PokemonCardItem(pokemonEncontrado!!)

                Text(
                    text = "Voltar para a lista",
                    modifier = Modifier
                        .padding(20.dp)
                        .clickable {
                            pesquisa = ""
                            // Limpa o resultado e permite visualizar a lista novamente.
                            pokedexViewModel.limparPesquisa()
                        },
                    color = Color(183, 129, 227),
                    fontWeight = FontWeight.Bold
                )
            }

            else -> {
                Text(
                    text = "Pokémon",
                    modifier = Modifier.padding(horizontal = 20.dp),
                    color = Color(183, 129, 227),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        horizontal = 16.dp,
                        vertical = 12.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(listaPokemons) { pokemon ->
                        PokemonCardItem(pokemon)
                    }
                }
            }
        }
    }
}

// CARD INDIVIDUAL DO POKÉMON
@Composable
private fun PokemonCardItem(pokemon: Pokemon ) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .border(
                width = 1.5.dp,
                color = Color(183, 129, 227),
                shape = RoundedCornerShape(14.dp)
            ),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(252, 248, 255)
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp, end = 12.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = "#${pokemon.id.toString().padStart(3, '0')}",
                    color = Color(183, 129, 227),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "◉",
                    fontSize = 48.sp,
                    color = Color(183, 129, 227)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp)
                    .background(Color(183, 129, 227)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = pokemon.name.replaceFirstChar {
                        it.uppercase()
                    },
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
