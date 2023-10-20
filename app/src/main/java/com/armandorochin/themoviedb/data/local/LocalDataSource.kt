package com.armandorochin.themoviedb.data.local

import com.armandorochin.themoviedb.data.remote.MovieRemote
import com.armandorochin.themoviedb.data.remote.toMovieLocal
import com.armandorochin.themoviedb.domain.model.Movie
import com.armandorochin.themoviedb.domain.model.toMovieLocal
import javax.inject.Inject


class LocalDataSource @Inject constructor(
    private val dao: MoviesDao
){
    suspend fun getMoviesFromDb() : List<Movie>{
        return dao.getAll().map { it.toMovie() }
    }

    suspend fun updateMovie(movie: Movie){
        dao.update(movie.toMovieLocal())
    }

    suspend fun insertAll(movies: List<MovieRemote>){
        dao.insertAll(movies.map { it.toMovieLocal() })
    }

    suspend fun count(): Int{
        return dao.count()
    }

    suspend fun deleteAll(){
        dao.deleteAll()
    }
}