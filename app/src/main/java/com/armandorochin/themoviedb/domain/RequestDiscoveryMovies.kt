package com.armandorochin.themoviedb.domain

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.armandorochin.themoviedb.data.MoviesRepository
import com.armandorochin.themoviedb.domain.model.Movie
import javax.inject.Inject

class RequestDiscoveryMovies @Inject constructor(
    private val repository: MoviesRepository
) {
    operator fun invoke(): LiveData<PagingData<Movie>> {
        return repository.getDiscoveryMovies()
    }
}