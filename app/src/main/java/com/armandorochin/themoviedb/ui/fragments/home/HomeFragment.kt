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
import com.armandorochin.themoviedb.data.remote.RemoteMediator.Companion.CAT_DISCOVER
import com.armandorochin.themoviedb.data.remote.RemoteMediator.Companion.CAT_NOW_PLAYING
import com.armandorochin.themoviedb.data.remote.RemoteMediator.Companion.CAT_TOP_RATED
import com.armandorochin.themoviedb.data.remote.RemoteMediator.Companion.CAT_UPCOMING
import com.armandorochin.themoviedb.databinding.FragmentHomeBinding
import com.armandorochin.themoviedb.domain.model.Movie
import com.armandorochin.themoviedb.ui.fragments.about.AboutFragment
import com.armandorochin.themoviedb.ui.fragments.movie.MovieFragment
import com.armandorochin.themoviedb.ui.fragments.movies.MoviesAdapter
import com.armandorochin.themoviedb.ui.fragments.movies.MoviesFragment
import com.armandorochin.themoviedb.ui.MainActivity
import com.armandorochin.themoviedb.ui.fragments.movies.MoviesFragment.Companion.categoryKey
import com.armandorochin.themoviedb.ui.fragments.movies.MoviesFragment.Companion.titleKey
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment: Fragment(), MenuProvider {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()
    private var discoverdapter = MoviesAdapter(680, true) { onMovieClicked(it) }
    private var nowPlayingAdapter = MoviesAdapter(360, false) { onMovieClicked(it) }
    private var topRatedAdapter = MoviesAdapter(680, false) { onMovieClicked(it) }
    private var upcomingAdapter = MoviesAdapter(360, false) { onMovieClicked(it) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setupRecycler(binding.rvDiscoveryMovies, discoverdapter, CAT_DISCOVER)
        setupRecycler(binding.rvNowplayingMovies, nowPlayingAdapter, CAT_NOW_PLAYING)
        setupRecycler(binding.rvTopMovies, topRatedAdapter, CAT_TOP_RATED)
        setupRecycler(binding.rvPopularMovies, upcomingAdapter, CAT_UPCOMING)

        setupUI()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar?.show()
    }

    private fun setupRecycler(rv:RecyclerView, _adapter: MoviesAdapter?, category:String) {
        rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv.adapter = _adapter

        homeViewModel.getMoviesLiveData(category).observe(viewLifecycleOwner){ movies ->
            viewLifecycleOwner.lifecycleScope.launch{
                _adapter?.submitData(movies)
            }
        }
    }
    private fun setupUI(){
        (activity as MainActivity).supportActionBar?.hide()
        (activity as MainActivity).supportActionBar?.title = getString(R.string.app_name)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as MainActivity).supportActionBar?.setDisplayShowHomeEnabled(false)

        binding.tvDiscoveryMore.setOnClickListener { listenerConfiguration(getString(R.string.discover), CAT_DISCOVER) }
        binding.tvNowplayingMore.setOnClickListener { listenerConfiguration(getString(R.string.nowplaying), CAT_NOW_PLAYING) }
        binding.tvTopMore.setOnClickListener { listenerConfiguration(getString(R.string.toprated), CAT_TOP_RATED) }
        binding.tvPopularMore.setOnClickListener { listenerConfiguration(getString(R.string.popular), CAT_UPCOMING) }

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun listenerConfiguration(title:String, category: String){
        val args = Bundle()
        val fragment = MoviesFragment()

        args.putString(titleKey, title)
        args.putString(categoryKey, category)
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