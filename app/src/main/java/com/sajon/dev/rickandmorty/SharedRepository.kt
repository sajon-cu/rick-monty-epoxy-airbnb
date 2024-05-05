package com.sajon.dev.rickandmorty

class SharedRepository {
    suspend fun getCharacterById(id: Int): GetCharacterByIdResponse? {
        val response = NetworkLayer.apiClient.getCharacterById(id)

        if(response.isSuccessful) {
            return response.body()!!
        }

        return null
    }
}