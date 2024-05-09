package com.sajon.dev.rickandmorty.episodes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.sajon.dev.rickandmorty.R
import com.sajon.dev.rickandmorty.databinding.FragmentEpisodeListBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EpisodeListFragment : Fragment(R.layout.fragment_episode_list) {
    private val viewModel: EpisodeViewModel by viewModels()
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

        val epoxyController = EpisodeListEpoxyController { episodeClickedId ->
//            val navDirections = NavGraphDirections.actionGlobalToEpisodeDetailBottomSheetFragment(
//                episodeId = episodeClickedId
//            )
//            findNavController().navigate(navDirections)
        }

        lifecycleScope.launch {
            viewModel.flow.collectLatest {
                epoxyController.submitData(it)
            }
        }

        binding.epoxyRecyclerView.setController(epoxyController)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}