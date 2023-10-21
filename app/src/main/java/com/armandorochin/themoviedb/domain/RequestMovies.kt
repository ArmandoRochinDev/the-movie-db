package com.armandorochin.themoviedb.domain

import androidx.lifecycle.LiveData
import com.armandorochin.themoviedb.data.MoviesRepository
import com.armandorochin.themoviedb.domain.model.Movie
import javax.inject.Inject

class RequestMovies @Inject constructor(
    private val repository: MoviesRepository
) {
    operator fun invoke(): LiveData<List<Movie>> {
        return repository.getMoviesFromRepository()
    }
}