package com.example.listadeexercicios06.model


data class PokemonListResponse(
    val count: Int = 0,
    val results: List<PokemonListItem> = emptyList()
)

data class PokemonListItem(
    val name: String,
    val url: String
) {
    val dex: Int
        get() = url.trimEnd('/').substringAfterLast("/").toIntOrNull() ?: 0

    // Propriedade que monta a URL da imagem sozinha
    val imagemUrl: String
        get() {
            val id = url.trimEnd('/').substringAfterLast("/")
            return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$dex.png"
        }
}