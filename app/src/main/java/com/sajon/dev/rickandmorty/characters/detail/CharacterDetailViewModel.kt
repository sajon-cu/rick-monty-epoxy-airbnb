package com.sajon.dev.rickandmorty.characters.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sajon.dev.rickandmorty.characters.CharacterRepository
import com.sajon.dev.rickandmorty.domain.models.Character
import kotlinx.coroutines.launch

class CharacterDetailViewModel : ViewModel() {
    private val repository = CharacterRepository()

    private val _characterByIdLiveData = MutableLiveData<Character?>()
    val characterByIdLiveData: LiveData<Character?> = _characterByIdLiveData

    fun fetchCharacter(characterId: Int) = viewModelScope.launch {
        val character = repository.getCharacterById(characterId)
        _characterByIdLiveData.postValue(character)
    }
}