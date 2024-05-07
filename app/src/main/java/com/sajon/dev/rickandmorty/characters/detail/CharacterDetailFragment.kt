package com.sajon.dev.rickandmorty.characters.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.airbnb.epoxy.EpoxyRecyclerView
import com.sajon.dev.rickandmorty.R
import com.sajon.dev.rickandmorty.SharedViewModel

class CharacterDetailFragment : Fragment() {
    private val sharedViewModel: SharedViewModel by lazy { ViewModelProvider(this)[SharedViewModel::class.java] }
    private val epoxyController = CharacterDetailsEpoxyController()
    private val safeArgs: CharacterDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_character_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)
        observeViewModel()

        val characterId = safeArgs.characterId
        sharedViewModel.refreshCharacter(characterId)
    }

    private fun initRecyclerView(view: View) {
        val epoxyRecyclerView = view.findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView)
        epoxyRecyclerView.setControllerAndBuildModels(epoxyController)
    }

    private fun observeViewModel() {
        sharedViewModel.characterByIdResponse.observe(viewLifecycleOwner) { character ->
            if(character == null) {
                Toast.makeText(requireContext(), "Unsuccessful network call!!", Toast.LENGTH_LONG).show()
                return@observe
            }

            epoxyController.character = character
        }
    }
}