package com.sajon.dev.rickandmorty.characters

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.bumptech.glide.Glide
import com.sajon.dev.rickandmorty.R
import com.sajon.dev.rickandmorty.databinding.ModelCharacterListItemBinding
import com.sajon.dev.rickandmorty.databinding.ModelCharacterListTitleBinding
import com.sajon.dev.rickandmorty.epoxy.LoadingEpoxyModel
import com.sajon.dev.rickandmorty.epoxy.ViewBindingKotlinModel
import com.sajon.dev.rickandmorty.network.response.GetCharacterByIdResponse
import java.util.Locale

class CharacterListPagingEpoxyController : PagedListEpoxyController<GetCharacterByIdResponse>() {
    override fun buildItemModel(
        currentPosition: Int,
        item: GetCharacterByIdResponse?
    ): EpoxyModel<*> {
        return CharacterGridItemEpoxyModel(
            item!!.image, item.name
        ).id(item.id)
    }

    override fun addModels(models: List<EpoxyModel<*>>) {
        if(models.isEmpty()) {
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }

        CharacterGridTitleEpoxyModel("Main Family")
            .id("main_family_header")
            .addTo(this)

        super.addModels(models.subList(0, 5))

        (models.subList(5, models.size) as List<CharacterGridItemEpoxyModel>).groupBy { it.name[0].uppercaseChar() }
            .forEach { mapEntry ->
                val character = mapEntry.key.toString().uppercase(Locale.US)
                CharacterGridTitleEpoxyModel(title = character)
                    .id(character)
                    .addTo(this)

                super.addModels(mapEntry.value)
            }
    }

    data class CharacterGridItemEpoxyModel(
        val imageUrl: String,
        val name: String
    ): ViewBindingKotlinModel<ModelCharacterListItemBinding>(R.layout.model_character_list_item) {
        override fun ModelCharacterListItemBinding.bind() {
            characterNameTextView.text = name
            Glide.with(characterImageView).load(imageUrl).into(characterImageView)
        }
    }

    class CharacterGridTitleEpoxyModel(private val title: String): ViewBindingKotlinModel<ModelCharacterListTitleBinding>(R.layout.model_character_list_title) {
        override fun ModelCharacterListTitleBinding.bind() {
            titleTextView.text = title
        }

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }
    }
}