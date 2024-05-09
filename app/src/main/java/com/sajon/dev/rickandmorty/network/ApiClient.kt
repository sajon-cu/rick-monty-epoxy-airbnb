package com.sajon.dev.rickandmorty.network

import com.sajon.dev.rickandmorty.network.response.GetCharacterByIdResponse
import com.sajon.dev.rickandmorty.network.response.GetCharacterPageResponse
import com.sajon.dev.rickandmorty.network.response.GetEpisodeByIdResponse
import com.sajon.dev.rickandmorty.network.response.GetEpisodesPageResponse
import retrofit2.Response

class ApiClient(private val rickAndMortyService: RickAndMortyService) {
    suspend fun getCharacterById(id: Int): SimpleResponse<GetCharacterByIdResponse> {
        return safeApiCall { rickAndMortyService.getCharacterById(id) }
    }

    suspend fun getCharacters(pageInt: Int): SimpleResponse<GetCharacterPageResponse> {
        return safeApiCall { rickAndMortyService.getCharacters(pageInt) }
    }

    suspend fun getEpisodeById(episodeId: Int): SimpleResponse<GetEpisodeByIdResponse> {
        return safeApiCall { rickAndMortyService.getEpisodeById(episodeId) }
    }

    suspend fun getEpisodeRange(episodeRange: String): SimpleResponse<List<GetEpisodeByIdResponse>> {
        return safeApiCall { rickAndMortyService.getEpisodeRange(episodeRange) }
    }

    suspend fun getEpisodesPage(pageIndex: Int): SimpleResponse<GetEpisodesPageResponse> {
        return safeApiCall { rickAndMortyService.getEpisodesPage(pageIndex) }
    }

    private inline fun <T> safeApiCall(apiCall: ()-> Response<T>): SimpleResponse<T> {
        return try {
            SimpleResponse.success(apiCall.invoke())
        } catch (ex: Exception) {
            SimpleResponse.failure(ex)
        }
    }
}