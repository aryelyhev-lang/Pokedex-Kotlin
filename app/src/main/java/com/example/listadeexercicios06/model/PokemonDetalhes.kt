
package com.example.listadeexercicios06.model

// Dados principais do Pokémon
data class PokemonDetalhes(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<PokemonTipoResposta>,
    val abilities: List<PokemonHabilidadeResposta>,
    val stats: List<PokemonStatusResposta>,
    val sprites: PokemonSprites
)

data class PokemonTipoResposta(
    val type: PokemonNome
)

data class PokemonHabilidadeResposta(
    val ability: PokemonNome
)

data class PokemonStatusResposta(
    val base_stat: Int,
    val stat: PokemonNome
)

data class PokemonNome(
    val name: String
)

data class PokemonSprites(
    val front_default: String?
)
