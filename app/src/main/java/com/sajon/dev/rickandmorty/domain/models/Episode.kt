package com.sajon.dev.rickandmorty.domain.models

data class Episode(
    val id: Int = 0,
    val name: String = "",
    val airDate: String = "",
    val seasonNumber: Int? = 0,
    val episodeNumber: Int? = 0
) {
    fun getFormattedSeasonTruncated(): String {
        return "S.$seasonNumber e.$episodeNumber"
    }
}