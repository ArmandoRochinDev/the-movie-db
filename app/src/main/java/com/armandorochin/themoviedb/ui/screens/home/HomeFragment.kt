package com.armandorochin.themoviedb.ui.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.armandorochin.themoviedb.databinding.FragmentHomeBinding
import com.armandorochin.themoviedb.domain.model.Movie
import com.armandorochin.themoviedb.ui.screens.detail.DetailMovieFragment
import com.armandorochin.themoviedb.ui.screens.discovery.DiscoveryAdapter
import com.armandorochin.themoviedb.ui.screens.discovery.DiscoveryMoviesFragment
import com.armandorochin.themoviedb.ui.screens.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment: Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()
    private var discoveryAdapter: DiscoveryAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setupDiscoveryRecycler()

        return binding.root
    }

    private fun setupDiscoveryRecycler() {
        discoveryAdapter = DiscoveryAdapter(true) { onMovieClicked(it) }
        binding.rvDiscoveryMovies.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvDiscoveryMovies.adapter = discoveryAdapter
        homeViewModel.getDiscoveryLiveData().observe(viewLifecycleOwner){ movies ->
            viewLifecycleOwner.lifecycleScope.launch{
                discoveryAdapter?.submitData(movies)
            }
        }

        binding.tvDiscover.text = "Discover"
        binding.tvDiscoveryMore.text = "More"
        binding.tvDiscoveryMore.setOnClickListener { (activity as MainActivity).loadFragmentToBackstack(DiscoveryMoviesFragment())}
    }

    private fun onMovieClicked(movie: Movie){
        (activity as MainActivity).loadFragmentToBackstack(DetailMovieFragment(movie.movieId))
    }
}