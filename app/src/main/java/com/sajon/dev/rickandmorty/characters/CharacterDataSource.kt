package com.sajon.dev.rickandmorty.characters

import androidx.paging.PageKeyedDataSource
import com.sajon.dev.rickandmorty.network.response.GetCharacterByIdResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CharacterDataSource(
    private val coroutineScope: CoroutineScope,
    private val repository: CharacterRepository
) : PageKeyedDataSource<Int, GetCharacterByIdResponse>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, GetCharacterByIdResponse>
    ) {
        coroutineScope.launch {
            val characters = repository.getCharacters(1)
            callback.onResult(characters, null, 2)
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, GetCharacterByIdResponse>
    ) {
        coroutineScope.launch {
            val characters = repository.getCharacters(params.key)
            callback.onResult(characters, params.key + 1)
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, GetCharacterByIdResponse>
    ) {
        TODO("Not yet implemented")
    }
}