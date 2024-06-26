package com.sajon.dev.rickandmorty.network.response

data class GetCharacterByIdResponse(
    val created: String,
    val episode: List<String> = emptyList(),
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
) {
    data class Location(
        val name: String,
        val url: String
    )
    
    data class Origin(
        val name: String,
        val url: String
    )
}