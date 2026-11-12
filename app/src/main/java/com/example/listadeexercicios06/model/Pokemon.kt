package com.example.listadeexercicios06.model

// Representa um Pokémon retornado pela PokéAPI.
data class Pokemon(
    val id: Int,
    val name: String
)

// Representa cada item da lista inicial da PokéAPI.
data class PokemonResultado(
    val name: String,
    val url: String
)

// Representa a resposta paginada da listagem.
data class PokemonListaResposta(
    val count: Int,
    val results: List<PokemonResultado>
)
