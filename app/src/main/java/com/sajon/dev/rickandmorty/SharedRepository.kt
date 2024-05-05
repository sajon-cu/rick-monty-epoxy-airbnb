package com.sajon.dev.rickandmorty

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