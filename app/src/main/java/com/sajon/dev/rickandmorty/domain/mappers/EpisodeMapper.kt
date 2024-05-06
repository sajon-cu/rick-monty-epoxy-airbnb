package com.sajon.dev.rickandmorty.domain.mappers

import com.sajon.dev.rickandmorty.domain.models.Episode
import com.sajon.dev.rickandmorty.network.response.GetEpisodeByIdResponse

object EpisodeMapper {
    fun buildForm(networkEpisode: GetEpisodeByIdResponse): Episode {
        return Episode(
            id = networkEpisode.id,
            name = networkEpisode.name,
            airDate = networkEpisode.air_date,
            episode = networkEpisode.episode,
        )
    }
}