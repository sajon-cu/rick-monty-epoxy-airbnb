package com.sajon.dev.rickandmorty.network.response

data class GetEpisodeByIdResponse(
    val air_date: String = "",
    val characters: List<String> = emptyList(),
    val created: String = "",
    val id: Int = 0,
    val episode: String = "",
    val name: String = "",
    val url: String = ""
)