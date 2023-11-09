package com.armandorochin.themoviedb.ui.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.armandorochin.themoviedb.R
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
    private var bigAdapter = DiscoveryAdapter(680, true) { onMovieClicked(it) }
    private var smallAdapter = DiscoveryAdapter(360, false) { onMovieClicked(it) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setupRecycler(binding.rvDiscoveryMovies, bigAdapter)
        setupRecycler(binding.rvNowplayingMovies, smallAdapter)
        setupRecycler(binding.rvTopMovies, bigAdapter)
        setupRecycler(binding.rvPopularMovies, smallAdapter)

        setupUI()

        return binding.root
    }

    private fun setupRecycler(rv:RecyclerView, _adapter: DiscoveryAdapter?) {
        rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv.adapter = _adapter

        homeViewModel.getDiscoveryLiveData().observe(viewLifecycleOwner){ movies ->
            viewLifecycleOwner.lifecycleScope.launch{
                _adapter?.submitData(movies)
            }
        }
    }

    private fun setupUI(){
        //binding.layoutToolbar.toolbar.title = "TMDb - ShowCase App"
        (activity as MainActivity).supportActionBar?.title = "TMDb - ShowCase App"

        binding.tvDiscoveryMore.setOnClickListener { (activity as MainActivity).addFragmentToBackstack(DiscoveryMoviesFragment( getString(R.string.discover) ))}
        binding.tvNowplayingMore.setOnClickListener { (activity as MainActivity).addFragmentToBackstack(DiscoveryMoviesFragment(getString(R.string.nowplaying)))}
        binding.tvTopMore.setOnClickListener { (activity as MainActivity).addFragmentToBackstack(DiscoveryMoviesFragment(getString(R.string.toprated)))}
        binding.tvPopularMore.setOnClickListener { (activity as MainActivity).addFragmentToBackstack(DiscoveryMoviesFragment(getString(R.string.popular)))}
    }

    private fun onMovieClicked(movie: Movie){
        (activity as MainActivity).addFragmentToBackstack(DetailMovieFragment(movie.movieId))
    }
}