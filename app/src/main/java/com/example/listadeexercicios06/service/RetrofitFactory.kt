
package com.example.listadeexercicios06.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Configura a conexão com a PokéAPI.
class RetrofitFactory {

    private val BASE_URL = "https://pokeapi.co/api/v2/pokemon/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getPokemonService(): PokemonService {
        return retrofit.create(PokemonService::class.java)
    }
}
