package com.armandorochin.themoviedb.data

import com.armandorochin.themoviedb.data.local.LocalDataSource
import com.armandorochin.themoviedb.data.remote.RemoteDataSource
import com.armandorochin.themoviedb.domain.model.Movie
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {
    suspend fun requestMovies() : List<Movie>{
        //TODO handle retrofit exceptions
        val isDbEmpty = localDataSource.count() == 0

        if(isDbEmpty){
            localDataSource.insertAll(remoteDataSource.getMovies().movies)
        }

        return localDataSource.getMoviesFromDb()
    }

    suspend fun updateMovie(movie: Movie){
        localDataSource.updateMovie(movie)
    }
}