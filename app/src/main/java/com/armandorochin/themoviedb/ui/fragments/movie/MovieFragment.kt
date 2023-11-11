package com.armandorochin.themoviedb.ui.fragments.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.armandorochin.themoviedb.databinding.FragmentMovieBinding
import com.armandorochin.themoviedb.di.NetworkModule
import com.armandorochin.themoviedb.domain.model.Movie
import com.armandorochin.themoviedb.ui.MainActivity
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import java.math.RoundingMode
import java.text.DecimalFormat

@AndroidEntryPoint
class MovieFragment(private val movieId:Int) : Fragment() {
    private var _binding:FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private val discoveryMovieViewModel: MovieViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)

        discoveryMovieViewModel.initViewModel(movieId)
        discoveryMovieViewModel.getMovieLiveData().observe(viewLifecycleOwner){ movie ->
            setupUI(movie)
        }

        (activity as MainActivity).supportActionBar?.hide()

        return binding.root
    }

    private fun setupUI(movie: Movie) {
        binding.detailsTitle.text = movie.title
        binding.detailsReleaseDate.text = movie.releaseDate
        binding.detailsVoteAverage.text = roundOffDecimal(movie.voteAverage).toString()
        binding.detailsOriginalLang.text = movie.originalLanguage
        binding.summary.text = movie.overview
        Glide.with(binding.detailsPoster.context).load("${NetworkModule.IMAGEURL_185}${movie.posterPath}").into(binding.detailsPoster)
        Glide.with(binding.detailsBackdrop.context)
            .load("${NetworkModule.IMAGEURL_ORIGINAL}${movie.backdropPath}")
            .into(binding.detailsBackdrop)

        binding.ivNavigationUp.setOnClickListener {
            val fm: FragmentManager = parentFragmentManager

            if (fm.backStackEntryCount > 0) {
                fm.popBackStack()
            }
        }
    }
    private fun roundOffDecimal(number: Double): Double {
        val df = DecimalFormat("#.#")
        df.roundingMode = RoundingMode.HALF_UP
        return df.format(number).toDouble()
    }
}