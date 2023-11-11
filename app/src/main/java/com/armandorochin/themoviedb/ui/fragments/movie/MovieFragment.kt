package com.armandorochin.themoviedb.ui.fragments.movie

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
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.armandorochin.themoviedb.R
import com.armandorochin.themoviedb.databinding.FragmentMovieBinding
import com.armandorochin.themoviedb.di.NetworkModule
import com.armandorochin.themoviedb.domain.model.Movie
import com.armandorochin.themoviedb.ui.MainActivity
import com.armandorochin.themoviedb.ui.fragments.about.AboutFragment
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment(private val movieId:Int) : Fragment(), MenuProvider {
    private var _binding:FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private val discoveryMovieViewModel: MovieViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)

        discoveryMovieViewModel.initViewModel(movieId)
        discoveryMovieViewModel.getMovieLiveData().observe(viewLifecycleOwner){ movie ->
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
        binding.detailsVoteAverage.text = "${movie.voteAverage}${getString(R.string.voteaverage_append)}"
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