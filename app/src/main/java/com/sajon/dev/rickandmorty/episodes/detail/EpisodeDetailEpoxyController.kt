package com.sajon.dev.rickandmorty.episodes.detail

import com.airbnb.epoxy.EpoxyController
import com.bumptech.glide.Glide
import com.sajon.dev.rickandmorty.R
import com.sajon.dev.rickandmorty.databinding.ModelCharacterListItemSquareBinding
import com.sajon.dev.rickandmorty.domain.models.Character
import com.sajon.dev.rickandmorty.epoxy.ViewBindingKotlinModel

class EpisodeDetailEpoxyController(private val characterList: List<Character>) : EpoxyController() {
    override fun buildModels() {
        characterList.forEach { character ->
            CharacterEpoxyModel(character.image, character.name).id(character.id).addTo(this)
        }
    }

    data class CharacterEpoxyModel(val imageUrl: String, val name: String) : ViewBindingKotlinModel<ModelCharacterListItemSquareBinding>(R.layout.model_character_list_item_square) {
        override fun ModelCharacterListItemSquareBinding.bind() {
            Glide.with(characterImageView).load(imageUrl).into(characterImageView)
            characterNameTextView.text = name
        }
    }
}