package com.sajon.dev.rickandmorty.characters

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.EpoxyRecyclerView
import com.sajon.dev.rickandmorty.R

class CharacterListFragment : Fragment() {
    private val epoxyController = CharacterListPagingEpoxyController(::onCharacterSelected)
    private val characterViewModel: CharactersViewModel by lazy { ViewModelProvider(this)[CharactersViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_character_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeViewModel()
    }

    private fun initRecyclerView() {
        val epoxyRecyclerView = view?.findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView)
        epoxyRecyclerView?.setController(epoxyController)
    }

    private fun observeViewModel() {
        characterViewModel.charactersPagedListLiveData.observe(viewLifecycleOwner) { response ->
            epoxyController.submitList(response)
        }
    }

    private fun onCharacterSelected(characterId: Int) {
        val direction = CharacterListFragmentDirections.actionCharacterListFragmentToCharacterDetailFragment(characterId)
        findNavController().navigate(direction)
    }
}