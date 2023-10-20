package com.armandorochin.themoviedb.domain

import com.armandorochin.themoviedb.data.MoviesRepository
import com.armandorochin.themoviedb.domain.model.Movie
import javax.inject.Inject

class GetMovies @Inject constructor(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(): List<Movie> {
        return repository.requestMovies()
    }
}