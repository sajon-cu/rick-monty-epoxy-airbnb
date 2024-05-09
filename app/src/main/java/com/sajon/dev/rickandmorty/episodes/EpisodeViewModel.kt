package com.sajon.dev.rickandmorty.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import com.sajon.dev.rickandmorty.Constants.PAGE_SIZE
import com.sajon.dev.rickandmorty.Constants.PREFETCH_DISTANCE
import kotlinx.coroutines.flow.map

class EpisodeViewModel : ViewModel() {
    private val episodesRepository: EpisodesRepository = EpisodesRepository()
    val flow = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(
            pageSize = PAGE_SIZE,
            prefetchDistance = PREFETCH_DISTANCE,
            enablePlaceholders = false
        )
    ) {
        EpisodePagingSource()
    }.flow.cachedIn(viewModelScope).map {
        it.insertSeparators { uiModel1: EpisodesUiModel?, uiModel2: EpisodesUiModel? ->
            if(uiModel1 == null || uiModel2 == null) {
                return@insertSeparators null
            }

            if(uiModel1 is EpisodesUiModel.Header || uiModel2 is EpisodesUiModel.Header) {
                return@insertSeparators null
            }

            val episode1 = (uiModel1 as EpisodesUiModel.Item).episode
            val episode2 = (uiModel2 as EpisodesUiModel.Item).episode

            if(episode1.seasonNumber != episode2.seasonNumber) {
                return@insertSeparators EpisodesUiModel.Header("Season ${episode2.seasonNumber}")
            } else {
                return@insertSeparators null
            }
        }
    }
}