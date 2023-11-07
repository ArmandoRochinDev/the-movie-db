package com.armandorochin.themoviedb.ui.screens.detail

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.armandorochin.themoviedb.R
import com.armandorochin.themoviedb.databinding.FragmentMovieDetailBinding
import com.armandorochin.themoviedb.di.NetworkModule
import com.armandorochin.themoviedb.domain.model.Movie
import com.armandorochin.themoviedb.ui.screens.discovery.DiscoveryMoviesFragment
import com.armandorochin.themoviedb.ui.screens.main.MainActivity
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieFragment(private val movieId:Int) : Fragment(){
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

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true)
            {
                override fun handleOnBackPressed() {
                    // Leave empty do disable back press or
                    // write your code which you want
                    (activity as MainActivity).loadFragment(DiscoveryMoviesFragment())
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )
    }

    private fun setupUI(movie: Movie) {
        binding.detailsTitle.text = movie.title
        binding.detailsReleaseDate.text = "${getString(R.string.estreno)}: ${movie.releaseDate} "
        binding.detailsVoteAverage.text = "${movie.voteAverage}/10"
        binding.summary.text = movie.overview
        Glide.with(binding.detailsPoster.context).load("${NetworkModule.IMAGEURL_185}${movie.posterPath}").into(binding.detailsPoster)
        Glide.with(binding.detailsBackdrop.context).load("${NetworkModule.IMAGEURL_ORIGINAL}${movie.backdropPath}").into(binding.detailsBackdrop)
    }
}