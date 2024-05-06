package com.sajon.dev.rickandmorty

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sajon.dev.rickandmorty.domain.models.Character
import com.sajon.dev.rickandmorty.network.response.GetCharacterByIdResponse
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel() {
    private val sharedRepository: SharedRepository = SharedRepository()
    private val _characterByIdLiveData = MutableLiveData<Character?>()
    val characterByIdResponse: LiveData<Character?> = _characterByIdLiveData

    fun refreshCharacter(id: Int) {
        viewModelScope.launch {
            val response = sharedRepository.getCharacterById(id)
            _characterByIdLiveData.postValue(response)
        }
    }
}