package com.sajon.dev.rickandmorty

import com.sajon.dev.rickandmorty.domain.mappers.CharacterMapper
import com.sajon.dev.rickandmorty.domain.models.Character
import com.sajon.dev.rickandmorty.network.NetworkLayer
import com.sajon.dev.rickandmorty.network.response.GetCharacterByIdResponse

class SharedRepository {
    suspend fun getCharacterById(id: Int): Character? {
        val request = NetworkLayer.apiClient.getCharacterById(id)

        if(request.isFailed) {
            return null
        }

        if(!request.isSuccessful) {
            return null
        }

        return CharacterMapper.buildFrom(request.body)
    }
}