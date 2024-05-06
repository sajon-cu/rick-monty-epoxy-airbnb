package com.sajon.dev.rickandmorty.network

import com.sajon.dev.rickandmorty.network.response.GetCharacterByIdResponse
import com.sajon.dev.rickandmorty.network.response.GetCharacterPageResponse
import com.sajon.dev.rickandmorty.network.response.GetEpisodeByIdResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyService {
    @GET("character/{character-id}")
    suspend fun getCharacterById(@Path("character-id") characterId: Int): Response<GetCharacterByIdResponse>

    @GET("character")
    suspend fun getCharacters(@Query("page") pageIndex: Int): Response<GetCharacterPageResponse>

    @GET("episode/{episode-id}")
    suspend fun getEpisodeById(@Path("episode-id") episodeId: Int): Response<GetEpisodeByIdResponse>

    @GET("episode/{episode-range}")
    suspend fun getEpisodeRange(@Path("episode-range") episodeRange: String): Response<List<GetEpisodeByIdResponse>>
}