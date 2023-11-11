package com.armandorochin.themoviedb.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.armandorochin.themoviedb.R
import com.armandorochin.themoviedb.databinding.FragmentHomeBinding
import com.armandorochin.themoviedb.domain.model.Movie
import com.armandorochin.themoviedb.ui.fragments.about.AboutFragment
import com.armandorochin.themoviedb.ui.fragments.movie.MovieFragment
import com.armandorochin.themoviedb.ui.fragments.movies.MoviesAdapter
import com.armandorochin.themoviedb.ui.fragments.movies.MoviesFragment
import com.armandorochin.themoviedb.ui.MainActivity
import com.armandorochin.themoviedb.ui.fragments.movies.MoviesFragment.Companion.titleKey
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment: Fragment(), MenuProvider {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()
    private var bigAdapter = MoviesAdapter(680, true) { onMovieClicked(it) }
    private var smallAdapter = MoviesAdapter(360, false) { onMovieClicked(it) }

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

    private fun setupRecycler(rv:RecyclerView, _adapter: MoviesAdapter?) {
        rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv.adapter = _adapter

        homeViewModel.getMoviesLiveData().observe(viewLifecycleOwner){ movies ->
            viewLifecycleOwner.lifecycleScope.launch{
                _adapter?.submitData(movies)
            }
        }
    }
    private fun setupUI(){
        (activity as MainActivity).supportActionBar?.title = getString(R.string.app_name)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as MainActivity).supportActionBar?.setDisplayShowHomeEnabled(false)

        binding.tvDiscoveryMore.setOnClickListener { listenerConfiguration(getString(R.string.discover)) }
        binding.tvNowplayingMore.setOnClickListener { listenerConfiguration(getString(R.string.nowplaying)) }
        binding.tvTopMore.setOnClickListener { listenerConfiguration(getString(R.string.toprated)) }
        binding.tvPopularMore.setOnClickListener { listenerConfiguration(getString(R.string.popular)) }

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun listenerConfiguration(title:String){
        var args = Bundle()
        var fragment = MoviesFragment()

        args.putString(titleKey, title)
        fragment.arguments = args
        (activity as MainActivity).addFragmentToBackstack( fragment )
    }

    private fun onMovieClicked(movie: Movie){
        (activity as MainActivity).addFragmentToBackstack(MovieFragment(movie.movieId))
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.main, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_about -> {
                (activity as MainActivity).addFragmentToBackstack(AboutFragment())
                true
            }
            else -> false
        }
    }
}