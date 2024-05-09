package com.sajon.dev.rickandmorty.episodes

import com.sajon.dev.rickandmorty.domain.models.Episode

sealed class EpisodesUiModel {
    class Item(val episode: Episode): EpisodesUiModel()
    class Header(val text: String): EpisodesUiModel()
}