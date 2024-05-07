package com.sajon.dev.rickandmorty.characters.detail

import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyController
import com.bumptech.glide.Glide
import com.sajon.dev.rickandmorty.R
import com.sajon.dev.rickandmorty.databinding.ModelCharacterDetailsDataPointBinding
import com.sajon.dev.rickandmorty.databinding.ModelCharacterDetailsHeaderBinding
import com.sajon.dev.rickandmorty.databinding.ModelCharacterDetailsImageBinding
import com.sajon.dev.rickandmorty.databinding.ModelEpisodeCarouselItemBinding
import com.sajon.dev.rickandmorty.databinding.ModelTitleBinding
import com.sajon.dev.rickandmorty.domain.models.Character
import com.sajon.dev.rickandmorty.domain.models.Episode
import com.sajon.dev.rickandmorty.epoxy.LoadingEpoxyModel
import com.sajon.dev.rickandmorty.epoxy.ViewBindingKotlinModel

class CharacterDetailsEpoxyController : EpoxyController() {
    var isLoading: Boolean = true
        set(value) {
            field = value
            if(field) {
                requestModelBuild()
            }
        }

    var character: Character ? = null
        set(value) {
            field = value
            if(field != null) {
                isLoading = false
                requestModelBuild()
            }
        }

    override fun buildModels() {
        if (isLoading) {
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }

        if(character == null) {
            // show error state
            return
        }

        //  add header model
        HeaderEpoxyModel(
            name = character!!.name,
            gender = character!!.gender,
            status = character!!.status
        ).id("header").addTo(this)

        // add image model
        ImageEpoxyModel(character!!.image).id("image").addTo(this)

        // Episode Carousel
        if(character!!.episodeList.isNotEmpty()) {
            val episodeModels = character!!.episodeList.map {episode ->
                EpisodeCarouselItem(episode).id(episode.id)
            }

            TitleEpoxyModel(title = "Episodes").id("title_episodes").addTo(this)
            CarouselModel_()
                .models(episodeModels)
                .id("episode_carousel")
                .addTo(this)
        }

        // add the data point model(s)
        DataPointEpoxyModel(
            title = "Origin",
            description = character!!.origin.name
        ).id("data_point_1").addTo(this)

        DataPointEpoxyModel(
            title = "Species",
            description = character!!.species
        ).id("data_point_2").addTo(this)
    }

    data class HeaderEpoxyModel(
        val name: String,
        val gender: String,
        val status: String
    ): ViewBindingKotlinModel<ModelCharacterDetailsHeaderBinding>(R.layout.model_character_details_header) {
        override fun ModelCharacterDetailsHeaderBinding.bind() {
            nameTextView.text = name
            aliveTextView.text = status

            if(gender.equals("male", true)) {
                genderImageView.setImageResource(R.drawable.ic_male_24)
            } else {
                genderImageView.setImageResource(R.drawable.ic_female_24)
            }
        }
    }

    data class ImageEpoxyModel(val imageUrl: String) : ViewBindingKotlinModel<ModelCharacterDetailsImageBinding>(
        R.layout.model_character_details_image
    ) {
        override fun ModelCharacterDetailsImageBinding.bind() {
            // Picasso.get().load(imageUrl).into(headerImageView)
            Glide.with(headerImageView).load(imageUrl).into(headerImageView)
        }
    }

    data class DataPointEpoxyModel(
        val title: String,
        val description: String
    ) : ViewBindingKotlinModel<ModelCharacterDetailsDataPointBinding>(R.layout.model_character_details_data_point) {
        override fun ModelCharacterDetailsDataPointBinding.bind() {
            labelTextView.text = title
            textView.text = description
        }
    }

    data class EpisodeCarouselItem(val episode: Episode): ViewBindingKotlinModel<ModelEpisodeCarouselItemBinding>(
        R.layout.model_episode_carousel_item
    ) {
        override fun ModelEpisodeCarouselItemBinding.bind() {
            episodeTextView.text = episode.episode
            episodeDetailsTextView.text = "${episode.name}\n${episode.airDate}"
        }
    }

    data class TitleEpoxyModel(private val title: String) : ViewBindingKotlinModel<ModelTitleBinding>(
        R.layout.model_title
    ) {
        override fun ModelTitleBinding.bind() {
            titleTextView.text = title
        }
    }
}