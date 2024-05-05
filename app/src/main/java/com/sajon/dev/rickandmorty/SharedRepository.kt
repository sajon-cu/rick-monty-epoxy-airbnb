package com.sajon.dev.rickandmorty

import com.sajon.dev.rickandmorty.network.NetworkLayer
import com.sajon.dev.rickandmorty.network.response.GetCharacterByIdResponse

class SharedRepository {
    suspend fun getCharacterById(id: Int): GetCharacterByIdResponse? {
        val request = NetworkLayer.apiClient.getCharacterById(id)

        if(request.isFailed) {
            return null
        }

        if(!request.isSuccessful) {
            return null
        }

        return request.body
    }
}