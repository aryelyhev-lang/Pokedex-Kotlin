package com.example.listadeexercicios06.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Arquivo responsavel por: Configurar a conexão com a Api utilizando Retrofit
class RetrofitFactory {

    //  Endereço da API de pokemons
    private val BASE_URL = "https://pokeapi.co/api/v2/pokemon/ditto"



    // Cria o cliente HTTP padrão
    private val retrofitFactory = Retrofit
        .Builder()
        .baseUrl(BASE_URL)                                      // URL base de todas as chamadas
        .addConverterFactory(GsonConverterFactory.create())    // usa o Gson para converter o JSON
        .build()

    // Devolve o cliente já configurado com as funções da interface EnderecoService
    fun getPokemonService(): PokemonService {
        return retrofitFactory.create(PokemonService::class.java)
    }
}