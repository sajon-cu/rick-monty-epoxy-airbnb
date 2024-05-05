package com.sajon.dev.rickandmorty.network.response

class GetCharacterPageResponse(
    val info: Info,
    val results: List<GetCharacterByIdResponse> = emptyList()
) {
    data class Info(
        val count: Int = 0,
        val pages: Int = 0,
        val next: String? = null,
        val prev: String? = null
    )
}