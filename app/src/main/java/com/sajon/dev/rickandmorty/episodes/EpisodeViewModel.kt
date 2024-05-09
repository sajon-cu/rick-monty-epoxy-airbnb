package com.sajon.dev.rickandmorty.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
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
        it
    }
}