package com.sajon.dev.rickandmorty.domain.mappers

import com.sajon.dev.rickandmorty.domain.models.Episode
import com.sajon.dev.rickandmorty.network.response.GetEpisodeByIdResponse

object EpisodeMapper {
    fun buildFrom(networkEpisode: GetEpisodeByIdResponse): Episode {
        return Episode(
            id = networkEpisode.id,
            name = networkEpisode.name,
            airDate = networkEpisode.air_date,
            seasonNumber = getSeasonNumberFromEpisodeString(networkEpisode.episode),
            episodeNumber = getEpisodeNumberFromEpisodeString(networkEpisode.episode),
        )
    }

    // S01E01
    private fun getSeasonNumberFromEpisodeString(episode: String): Int? {
        val endIndex = episode.indexOfFirst { it -> it.equals('e', true) }
        if(endIndex == -1) {
            return 0
        }

        return episode.substring(1, endIndex).toIntOrNull()
    }

    private fun getEpisodeNumberFromEpisodeString(episode: String): Int? {
        val startIndex = episode.indexOfFirst { it -> it.equals('e', true) }
        if(startIndex == -1) {
            return 0
        }

        return episode.substring(startIndex + 1).toIntOrNull()
    }
}