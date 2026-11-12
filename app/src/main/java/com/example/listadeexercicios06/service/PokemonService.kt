
package com.example.listadeexercicios06.service

import com.example.listadeexercicios06.model.Pokemon
import com.example.listadeexercicios06.model.PokemonListaResposta
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import com.example.listadeexercicios06.model.PokemonDetalhes

interface PokemonService {

    // Pesquisa um Pokémon pelo nome ou ID.
    @GET("https://pokeapi.co/api/v2/pokemon/{idOrName}")
    suspend fun getPokemonByIdOrName(
        @Path("idOrName") idOrName: String
    ): Pokemon

    // Retorna a lista de Pokémon.
    @GET("https://pokeapi.co/api/v2/pokemon/")
    suspend fun getAllPokemon(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): PokemonListaResposta

    @GET("https://pokeapi.co/api/v2/pokemon/{idOrName}")
    suspend fun getPokemonDetalhes(
        @Path("idOrName") idOrName: String
    ): PokemonDetalhes

}
