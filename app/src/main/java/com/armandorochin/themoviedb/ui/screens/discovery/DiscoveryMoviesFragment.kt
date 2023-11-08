package com.armandorochin.themoviedb.ui.screens.discovery

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.armandorochin.themoviedb.databinding.FragmentDiscoveryMoviesBinding
import com.armandorochin.themoviedb.domain.model.Movie
import com.armandorochin.themoviedb.ui.calculateNoOfColumns
import com.armandorochin.themoviedb.ui.screens.detail.DetailMovieFragment
import com.armandorochin.themoviedb.ui.screens.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DiscoveryMoviesFragment(private val title:String) : Fragment() {

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
        (activity as MainActivity).supportActionBar?.title = title

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                this.remove()
                activity?.onBackPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }
    private fun setupRecycler() {
        adapter = DiscoveryAdapter(-1, false) { onMovieClicked(it) }
        binding.rvMovies.layoutManager = GridLayoutManager(context, calculateNoOfColumns(requireContext(),180f))
        binding.rvMovies.adapter = adapter
        discoveryViewModel.getDiscoveryLiveData().observe(viewLifecycleOwner){ movies ->
            viewLifecycleOwner.lifecycleScope.launch{
                adapter?.submitData(movies)
            }
        }
    }
    private fun onMovieClicked(movie: Movie){
        (activity as MainActivity).addFragmentToBackstack(DetailMovieFragment(movie.movieId))
    }
}