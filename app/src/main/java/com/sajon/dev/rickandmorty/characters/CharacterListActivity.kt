package com.sajon.dev.rickandmorty.characters

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.airbnb.epoxy.EpoxyRecyclerView
import com.sajon.dev.rickandmorty.MainActivity
import com.sajon.dev.rickandmorty.R
import com.sajon.dev.rickandmorty.SharedViewModel

class CharacterListActivity : AppCompatActivity() {
    private val epoxyController = CharacterListPagingEpoxyController(::onCharacterSelected)

    private val characterViewModel: CharactersViewModel by lazy {
        ViewModelProvider(this)[CharactersViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_list)

        initRecyclerView()
        observeViewModel()
    }

    private fun initRecyclerView() {
        val epoxyRecyclerView = findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView)
        epoxyRecyclerView.setController(epoxyController)
    }

    private fun observeViewModel() {
        characterViewModel.charactersPagedListLiveData.observe(this) { response ->
            epoxyController.submitList(response)
        }
    }

    private fun onCharacterSelected(characterId: Int) {
        startActivity(MainActivity.getIntent(this, characterId))
    }
}