package com.sajon.dev.rickandmorty

import retrofit2.Response

class ApiClient(private val rickAndMortyService: RickAndMortyService) {
    suspend fun getCharacterById(id: Int): SimpleResponse<GetCharacterByIdResponse> {
        return safeApiCall { rickAndMortyService.getCharacterById(id) }
    }

    private inline fun <T> safeApiCall(apiCall: ()-> Response<T>): SimpleResponse<T> {
        return try {
            SimpleResponse.success(apiCall.invoke())
        } catch (ex: Exception) {
            SimpleResponse.failure(ex)
        }
    }
}