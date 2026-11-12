
package com.example.listadeexercicios06.repository

import com.example.listadeexercicios06.model.Pokemon
import com.example.listadeexercicios06.service.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokemonRepository {

    private val service = RetrofitFactory().getPokemonService()

    // Busca um Pokémon pelo nome ou ID.
    suspend fun buscarPokemon(pesquisa: String): Pokemon {
        return withContext(Dispatchers.IO) {
            service.getPokemonByIdOrName(pesquisa)
        }
    }

    // Busca a lista inicial de Pokémon.
    suspend fun buscarListaPokemons(): List<Pokemon> {
        return withContext(Dispatchers.IO) {

            val resposta = service.getAllPokemon()

            resposta.results.map { resultado ->

                val id = resultado.url
                    .trimEnd('/')
                    .substringAfterLast('/')
                    .toInt()

                Pokemon(
                    id = id,
                    name = resultado.name
                )
            }
        }
    }
}
