package com.sajon.dev.rickandmorty.characters

import com.sajon.dev.rickandmorty.network.NetworkLayer
import com.sajon.dev.rickandmorty.network.response.GetCharacterPageResponse

class CharacterRepository() {
    suspend fun getCharacters(pageIndex: Int): GetCharacterPageResponse? {
        val request = NetworkLayer.apiClient.getCharacters(pageIndex)
        if(request.isFailed || !request.isSuccessful) {
            return null
        }

        return request.body
    }
}