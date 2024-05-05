package com.sajon.dev.rickandmorty.characters

import androidx.paging.DataSource
import com.sajon.dev.rickandmorty.network.response.GetCharacterByIdResponse
import com.sajon.dev.rickandmorty.network.response.GetCharacterPageResponse
import kotlinx.coroutines.CoroutineScope

class CharacterDataSourceFactory(
    private val coroutineScope: CoroutineScope,
    private val repository: CharacterRepository
) : DataSource.Factory<Int, GetCharacterByIdResponse>() {
    override fun create(): DataSource<Int, GetCharacterByIdResponse> {
        return CharacterDataSource(coroutineScope, repository)
    }
}