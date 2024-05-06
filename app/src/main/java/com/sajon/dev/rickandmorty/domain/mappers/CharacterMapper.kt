package com.sajon.dev.rickandmorty.domain.mappers

import com.sajon.dev.rickandmorty.domain.models.Character
import com.sajon.dev.rickandmorty.network.response.GetCharacterByIdResponse

object CharacterMapper {
    fun buildFrom(response: GetCharacterByIdResponse): Character {
        return Character(
            episode = emptyList(), // TODO
            gender = response.gender,
            id = response.id,
            image = response.image,
            location = Character.Location(
                name = response.location.name,
                url = response.location.url
            ),
            name = response.name,
            origin = Character.Origin(
                name = response.origin.name,
                url = response.origin.url
            ),
            species = response.species,
            status = response.status
        )
    }
}