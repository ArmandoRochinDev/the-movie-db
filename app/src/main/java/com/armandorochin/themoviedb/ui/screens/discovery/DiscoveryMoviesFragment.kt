package com.armandorochin.themoviedb.ui.screens.discovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.armandorochin.themoviedb.databinding.FragmentDiscoveryMoviesBinding
import com.armandorochin.themoviedb.ui.calculateNoOfColumns
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DiscoveryMoviesFragment : Fragment() {

    private var _binding:FragmentDiscoveryMoviesBinding? = null
    private val binding get() = _binding!!

    private val discoveryViewModel: DiscoveryViewModel by viewModels()
    private var adapter: DiscoveryAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiscoveryMoviesBinding.inflate(inflater, container, false)

        setupRecycler()

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }
    private fun setupRecycler() {
        adapter = DiscoveryAdapter()
        binding.rvMovies.layoutManager = GridLayoutManager(context, calculateNoOfColumns(requireContext(),180f))
        binding.rvMovies.adapter = adapter
        discoveryViewModel.getDiscoveryLiveData().observe(viewLifecycleOwner){ movies ->
            viewLifecycleOwner.lifecycleScope.launch{
                adapter?.submitData(movies)
            }
        }
    }
}