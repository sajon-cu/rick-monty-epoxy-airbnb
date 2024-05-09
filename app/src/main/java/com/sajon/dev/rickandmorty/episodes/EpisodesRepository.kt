package com.sajon.dev.rickandmorty.episodes

import com.sajon.dev.rickandmorty.network.NetworkLayer
import com.sajon.dev.rickandmorty.network.response.GetEpisodesPageResponse

class EpisodesRepository {
    suspend fun fetchEpisodesPage(pageIndex: Int): GetEpisodesPageResponse? {
        val pageRequest = NetworkLayer.apiClient.getEpisodesPage(pageIndex)
        if(!pageRequest.isSuccessful) {
            return null
        }

        return pageRequest.body
    }
}