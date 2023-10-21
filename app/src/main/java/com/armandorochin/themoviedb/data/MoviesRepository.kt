package com.armandorochin.themoviedb.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.armandorochin.themoviedb.data.local.LocalDataSource
import com.armandorochin.themoviedb.data.remote.RemoteDataSource
import com.armandorochin.themoviedb.domain.model.Movie
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {
    fun getMoviesFromRepository(): LiveData<List<Movie>> = liveData{
        emitSource(getMovies())
    }

    private suspend fun getMovies(): LiveData<List<Movie>>{
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