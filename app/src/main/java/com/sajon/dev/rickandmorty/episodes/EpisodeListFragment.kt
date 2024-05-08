package com.sajon.dev.rickandmorty.episodes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sajon.dev.rickandmorty.R
import com.sajon.dev.rickandmorty.databinding.FragmentEpisodeListBinding

class EpisodeListFragment : Fragment(R.layout.fragment_episode_list) {
    private var _binding: FragmentEpisodeListBinding? = null
    private val binding: FragmentEpisodeListBinding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpisodeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}