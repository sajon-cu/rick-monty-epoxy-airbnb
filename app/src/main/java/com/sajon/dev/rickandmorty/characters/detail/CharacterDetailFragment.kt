package com.sajon.dev.rickandmorty.characters.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.airbnb.epoxy.EpoxyRecyclerView
import com.sajon.dev.rickandmorty.MainNavGraphDirections
import com.sajon.dev.rickandmorty.R

class CharacterDetailFragment : Fragment() {
    private val viewModel: CharacterDetailViewModel by viewModels()
    private val epoxyController = CharacterDetailsEpoxyController(::onEpisodeClicked)
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
        viewModel.fetchCharacter(characterId)
    }

    private fun initRecyclerView(view: View) {
        val epoxyRecyclerView = view.findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView)
        epoxyRecyclerView.setControllerAndBuildModels(epoxyController)
    }

    private fun observeViewModel() {
        viewModel.characterByIdLiveData.observe(viewLifecycleOwner) { character ->
            if(character == null) {
                Toast.makeText(requireContext(), "Unsuccessful network call!!", Toast.LENGTH_LONG).show()
                return@observe
            }

            epoxyController.character = character
        }
    }

    private fun onEpisodeClicked(episodeId: Int) {
        val navDirections = MainNavGraphDirections.actionGlobalToEpisodeDetailBottomSheetFragment(episodeId)
        findNavController().navigate(navDirections)
    }
}