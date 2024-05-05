package com.sajon.dev.rickandmorty.characters

import com.sajon.dev.rickandmorty.network.response.GetCharacterByIdResponse
import kotlinx.coroutines.CoroutineScope

class CharacterRepository() {
    suspend fun getCharacters(pageIndex: Int): List<GetCharacterByIdResponse> {
        return emptyList()
    }
}