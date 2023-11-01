package com.armandorochin.themoviedb.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.paging.PagingData
import androidx.paging.map
import com.armandorochin.themoviedb.data.MoviesRepository
import com.armandorochin.themoviedb.data.local.movie.toMovie
import com.armandorochin.themoviedb.domain.model.Movie
import javax.inject.Inject

class RequestDiscoveryMovies @Inject constructor(
    private val repository: MoviesRepository
) {
    operator fun invoke(): LiveData<PagingData<Movie>> {
        return repository.getDiscoveryMovies().map { pagingData ->
            pagingData.map { it.toMovie() }
        }
    }
}