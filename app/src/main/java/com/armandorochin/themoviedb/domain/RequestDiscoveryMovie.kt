package com.armandorochin.themoviedb.domain

import androidx.lifecycle.LiveData
import com.armandorochin.themoviedb.data.MoviesRepository
import com.armandorochin.themoviedb.domain.model.Movie
import javax.inject.Inject

class RequestDiscoveryMovie @Inject constructor(
    private val repository: MoviesRepository
) {
    operator fun invoke(movieId: Int): LiveData<Movie> {
        return repository.getDiscoveryMovie(movieId)
    }
}