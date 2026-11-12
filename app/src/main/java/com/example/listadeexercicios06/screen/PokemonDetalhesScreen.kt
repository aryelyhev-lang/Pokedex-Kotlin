package com.example.listadeexercicios06.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.listadeexercicios06.model.PokemonDetalhes
import com.example.listadeexercicios06.screen.pokedex.PokedexViewModel

// Define uma cor para cada tipo de Pokémon
private enum class TipoPokemon(val cor: Color) {
    FIRE(Color(239, 100, 70)),
    WATER(Color(90, 150, 230)),
    GRASS(Color(110, 190, 110)),
    ELECTRIC(Color(245, 200, 70)),
    PSYCHIC(Color(190, 110, 190)),
    ICE(Color(120, 200, 210)),
    FIGHTING(Color(190, 90, 70)),
    POISON(Color(160, 100, 190)),
    GROUND(Color(190, 150, 100)),
    FLYING(Color(150, 160, 220)),
    BUG(Color(150, 180, 80)),
    ROCK(Color(170, 150, 110)),
    GHOST(Color(110, 100, 160)),
    DRAGON(Color(110, 100, 220)),
    DARK(Color(100, 90, 90)),
    STEEL(Color(150, 160, 170)),
    FAIRY(Color(230, 150, 190)),
    NORMAL(Color(170, 170, 150));

    companion object {
        fun buscarCor(tipo: String): Color {
            return values().firstOrNull {
                it.name.equals(tipo, ignoreCase = true)
            }?.cor ?: Color.Gray
        }
    }
}

@Composable
fun PokemonDetalhesScreen(
    pokemonId: Int,
    pokedexViewModel: PokedexViewModel,
    onVoltar: () -> Unit
) {
    val pokemon by pokedexViewModel.pokemonDetalhes.collectAsState()
    val carregando by pokedexViewModel.carregando.collectAsState()
    val erro by pokedexViewModel.mensagemErro.collectAsState()

    LaunchedEffect(pokemonId) {
        pokedexViewModel.buscarDetalhes(pokemonId)
    }

    val cor = TipoPokemon.buscarCor(
        pokemon?.types?.firstOrNull()?.type?.name ?: ""
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(cor.copy(alpha = 0.15f))
            .verticalScroll(rememberScrollState())
    ) {
        // Cabeçalho
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(cor)
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onVoltar) {
                Text("← Voltar", color = Color.White)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Pokédex",
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Estados da tela
        when {
            carregando -> {
                Text(
                    text = "Carregando Pokémon...",
                    modifier = Modifier.padding(20.dp)
                )
            }

            erro.isNotEmpty() -> {
                Text(
                    text = erro,
                    modifier = Modifier.padding(20.dp)
                )
            }

            pokemon != null -> {
                ConteudoDetalhes(pokemon!!, cor)
            }
        }
    }
}

@Composable
private fun ConteudoDetalhes(
    pokemon: PokemonDetalhes,
    cor: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Nome e identificação
        Text(
            text = "#${pokemon.id.toString().padStart(3, '0')}",
            color = cor,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = pokemon.name.replaceFirstChar { it.uppercase() },
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = cor
        )

        // Imagem
        AsyncImage(
            model = pokemon.sprites.front_default,
            contentDescription = pokemon.name,
            modifier = Modifier.size(190.dp)
        )

        // Tipos
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            pokemon.types.forEach { tipo ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = cor
                    )
                ) {
                    Text(
                        text = tipo.type.name.uppercase(),
                        color = Color.White,
                        modifier = Modifier.padding(12.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Informações
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Informações",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Text("Altura: ${pokemon.height / 10.0} m")
                Text("Peso: ${pokemon.weight / 10.0} kg")

                Text(
                    text = "Habilidades: ${
                        pokemon.abilities.joinToString {
                            it.ability.name
                        }
                    }"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Atributos
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Atributos",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                pokemon.stats.forEach { atributo ->
                    Text(
                        text = "${atributo.stat.name.uppercase()}: ${atributo.base_stat}",
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}
