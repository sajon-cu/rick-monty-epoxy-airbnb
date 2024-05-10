package com.sajon.dev.rickandmorty.characters

import com.sajon.dev.rickandmorty.domain.mappers.CharacterMapper
import com.sajon.dev.rickandmorty.domain.models.Character
import com.sajon.dev.rickandmorty.network.NetworkLayer
import com.sajon.dev.rickandmorty.network.response.GetCharacterByIdResponse
import com.sajon.dev.rickandmorty.network.response.GetCharacterPageResponse
import com.sajon.dev.rickandmorty.network.response.GetEpisodeByIdResponse

class CharacterRepository() {
    suspend fun getCharacters(pageIndex: Int): GetCharacterPageResponse? {
        val request = NetworkLayer.apiClient.getCharacters(pageIndex)
        if(request.isFailed || !request.isSuccessful) {
            return null
        }

        return request.body
    }

    suspend fun getCharacterById(id: Int): Character? {
        val request = NetworkLayer.apiClient.getCharacterById(id)

        if(request.isFailed) {
            return null
        }

        if(!request.isSuccessful) {
            return null
        }

        val networkEpisode = getEpisodesFromCharacterResponse(request.body)

        return CharacterMapper.buildFrom(
            response = request.body,
            episodes = networkEpisode
        )
    }

    private suspend fun getEpisodesFromCharacterResponse(
        characterByIdResponse: GetCharacterByIdResponse
    ): List<GetEpisodeByIdResponse> {
        val episodeRange = characterByIdResponse.episode.map {
            it.substring(it.lastIndexOf("/") + 1)
        }.toString()

        val request = NetworkLayer.apiClient.getEpisodeRange(episodeRange)

        if(request.isFailed || !request.isSuccessful) {
            return emptyList()
        }

        return request.body
    }
}