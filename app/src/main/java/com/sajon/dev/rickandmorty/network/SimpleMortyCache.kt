package com.sajon.dev.rickandmorty.network

import com.sajon.dev.rickandmorty.domain.models.Character

object SimpleMortyCache {
    val characterMap: MutableMap<Int, Character> = mutableMapOf()
}