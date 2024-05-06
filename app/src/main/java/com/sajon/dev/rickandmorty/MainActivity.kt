package com.sajon.dev.rickandmorty

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.airbnb.epoxy.EpoxyRecyclerView

class MainActivity : AppCompatActivity() {
    private val sharedViewModel: SharedViewModel by lazy {
        ViewModelProvider(this)[SharedViewModel::class.java]
    }

    private val epoxyController = CharacterDetailsEpoxyController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initRecyclerView()
        observeViewModel()

        val characterId = intent.getIntExtra(INTENT_EXTRA_CHARACTER_ID, 1)
        sharedViewModel.refreshCharacter(characterId)
    }

    private fun initRecyclerView() {
        val epoxyRecyclerView = findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView)
        epoxyRecyclerView.setControllerAndBuildModels(epoxyController)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else ->  super.onOptionsItemSelected(item)
        }

    }

    private fun observeViewModel() {
        sharedViewModel.characterByIdResponse.observe(this) { character ->
            if(character == null) {
                Toast.makeText(this@MainActivity, "Unsuccessful network call!!", Toast.LENGTH_LONG).show()
                return@observe
            }

            epoxyController.character = character
        }
    }

    companion object {
        private const val INTENT_EXTRA_CHARACTER_ID = "character_id_extra"

        fun getIntent(context: Context, characterId: Int): Intent {
            return Intent(context, MainActivity::class.java).apply {
                putExtra(INTENT_EXTRA_CHARACTER_ID, characterId)
            }
        }
    }
}