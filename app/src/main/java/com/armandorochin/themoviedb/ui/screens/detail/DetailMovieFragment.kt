package com.armandorochin.themoviedb.ui.screens.detail

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.armandorochin.themoviedb.R
import com.armandorochin.themoviedb.databinding.FragmentMovieDetailBinding
import com.armandorochin.themoviedb.di.NetworkModule
import com.armandorochin.themoviedb.domain.model.Movie
import com.armandorochin.themoviedb.ui.screens.about.AboutFragment
import com.armandorochin.themoviedb.ui.screens.discovery.DiscoveryMoviesFragment
import com.armandorochin.themoviedb.ui.screens.main.MainActivity
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieFragment(private val movieId:Int) : Fragment(), MenuProvider {
    private var _binding:FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    private val discoveryMovieViewModel: DetailMovieViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)

        discoveryMovieViewModel.initViewModel(movieId)
        discoveryMovieViewModel.getDiscoveryMovieLiveData().observe(viewLifecycleOwner){ movie ->
            setupUI(movie)
        }

        setupToolbar()

        return binding.root
    }

    private fun setupToolbar(){
        (activity as MainActivity).supportActionBar?.title = getString(R.string.detail)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setupUI(movie: Movie) {
        binding.detailsTitle.text = movie.title
        binding.detailsReleaseDate.text = "${getString(R.string.estreno)}: ${movie.releaseDate} "
        binding.detailsVoteAverage.text = "${movie.voteAverage}/10"
        binding.summary.text = movie.overview
        Glide.with(binding.detailsPoster.context).load("${NetworkModule.IMAGEURL_185}${movie.posterPath}").into(binding.detailsPoster)
        Glide.with(binding.detailsBackdrop.context).load("${NetworkModule.IMAGEURL_ORIGINAL}${movie.backdropPath}").into(binding.detailsBackdrop)
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
            android.R.id.home -> {
                val fm: FragmentManager = parentFragmentManager

                if (fm.backStackEntryCount > 0) {
                    fm.popBackStack()
                }
                true
            }
            else -> false
        }
    }
}