package com.armandorochin.themoviedb.data

import androidx.lifecycle.LiveData
import com.armandorochin.themoviedb.data.local.LocalDataSource
import com.armandorochin.themoviedb.data.remote.RemoteDataSource
import com.armandorochin.themoviedb.data.remote.toMovieLocal
import com.armandorochin.themoviedb.domain.Movie
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {
    val movies: LiveData<List<Movie>> = localDataSource.movies

    suspend fun requestMovies(){
        val isDbEmpty = localDataSource.count() == 0
        if(isDbEmpty){
            localDataSource.insertAll(remoteDataSource.getMovies().movies)
        }
    }

    suspend fun updateMovie(movie: Movie){
        localDataSource.updateMovie(movie)
    }
}