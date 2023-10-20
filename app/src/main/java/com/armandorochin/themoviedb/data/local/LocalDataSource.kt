package com.armandorochin.themoviedb.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.armandorochin.themoviedb.data.remote.MovieRemote
import com.armandorochin.themoviedb.data.remote.toMovieLocal
import com.armandorochin.themoviedb.domain.Movie
import com.armandorochin.themoviedb.domain.toMovieLocal
import javax.inject.Inject


class LocalDataSource @Inject constructor(
    private val dao: MoviesDao
){
    val movies: LiveData<List<Movie>> = dao.getMovies().map {
        movieList -> movieList.map { it.toMovie() }
    }

    suspend fun updateMovie(movie: Movie){
        dao.updateMovie(movie.toMovieLocal())
    }

    suspend fun insertAll(movies: List<MovieRemote>){
        dao.insertAll(movies.map { it.toMovieLocal() })
    }

    suspend fun count(): Int{
        return dao.count()
    }
}