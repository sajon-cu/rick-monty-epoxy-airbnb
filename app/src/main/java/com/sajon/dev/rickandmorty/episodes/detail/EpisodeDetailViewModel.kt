package com.sajon.dev.rickandmorty.episodes.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sajon.dev.rickandmorty.domain.models.Episode
import com.sajon.dev.rickandmorty.episodes.EpisodeRepository
import kotlinx.coroutines.launch

class EpisodeDetailViewModel : ViewModel() {
    private val repository = EpisodeRepository()

    private var _episodeLiveData = MutableLiveData<Episode?>()
    val episodeLiveData: LiveData<Episode?> = _episodeLiveData

    fun fetchEpisode(episodeId: Int) {
        viewModelScope.launch {
            val episode = repository.getEpisodeById(episodeId)

            _episodeLiveData.postValue(episode)
        }
    }
}