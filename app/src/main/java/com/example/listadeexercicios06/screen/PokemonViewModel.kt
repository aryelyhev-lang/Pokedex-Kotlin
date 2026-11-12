package com.example.listadeexercicios06.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listadeexercicios06.model.Pokemon
import com.example.listadeexercicios06.model.PokemonDetalhes
import com.example.listadeexercicios06.repository.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/* exemplo:
    - Carregar a lista de Pokémon
    - Controlar o carregamento
    - Armazenar os resultados da pesquisa
    - Tratar erros da API
    - Buscar Pokémon pelo nome ou ID
*/
class PokemonViewModel : ViewModel() {

    private val repository = PokemonRepository()

    // Lista todos os pokemons na tela inicial
    private val _listaPokemons = MutableStateFlow<List<Pokemon>>(emptyList())
    val listaPokemons: StateFlow<List<Pokemon>> = _listaPokemons

    // Pokémon encontrado na pesquisa
    private val _pokemonEncontrado = MutableStateFlow<Pokemon?>(null)
    val pokemonEncontrado: StateFlow<Pokemon?> = _pokemonEncontrado

    // Estado de carregamento
    private val _carregando = MutableStateFlow(false)
    val carregando: StateFlow<Boolean> = _carregando

    // Mensagem de erro
    private val _mensagemErro = MutableStateFlow("")
    val mensagemErro: StateFlow<String> = _mensagemErro

    init {
        carregarPokemons()
    }

    // Carrega os Pokémon ao abrir a tela
    fun carregarPokemons() {
        viewModelScope.launch {
            _carregando.value = true
            _mensagemErro.value = ""

            try {
                _listaPokemons.value = repository.buscarListaPokemons()
            } catch (e: Exception) {
                _mensagemErro.value = "Não foi possível carregar os Pokémon."
            } finally {
                _carregando.value = false
            }
        }
    }

    // Pesquisa um Pokémon pelo nome ou ID
    fun buscarPokemon(pesquisa: String) {

        if (pesquisa.isBlank()) return

        viewModelScope.launch {
            _carregando.value = true
            _mensagemErro.value = ""
            _pokemonEncontrado.value = null

            try {
                val identificador = pesquisa.trim().removePrefix("#")
                _pokemonEncontrado.value =
                    repository.buscarPokemon(identificador)

            } catch (e: Exception) {
                _mensagemErro.value = "Pokémon não encontrado!"
            } finally {
                _carregando.value = false
            }
        }
    }

    fun limparPesquisa() {
        _pokemonEncontrado.value = null
        _mensagemErro.value = ""
    }


    private val _pokemonDetalhes = MutableStateFlow<PokemonDetalhes?>(null)
    val pokemonDetalhes: StateFlow<PokemonDetalhes?> = _pokemonDetalhes

    fun buscarDetalhes(id: Int) {
        viewModelScope.launch {
            _carregando.value = true
            _mensagemErro.value = ""

            try {
                _pokemonDetalhes.value = repository.buscarDetalhes(id)
            } catch (e: Exception) {
                _mensagemErro.value = "Não foi possível carregar os detalhes."
            } finally {
                _carregando.value = false
            }
        }
    }

}