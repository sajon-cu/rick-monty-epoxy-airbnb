package com.sajon.dev.rickandmorty

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel() {
    private val sharedRepository: SharedRepository = SharedRepository()
    private val _characterByIdLiveData = MutableLiveData<GetCharacterByIdResponse?>()
    val characterByIdResponse: LiveData<GetCharacterByIdResponse?> = _characterByIdLiveData

    fun refreshCharacter(id: Int) {
        viewModelScope.launch {
            val response = sharedRepository.getCharacterById(id)
            _characterByIdLiveData.postValue(response)
        }
    }
}