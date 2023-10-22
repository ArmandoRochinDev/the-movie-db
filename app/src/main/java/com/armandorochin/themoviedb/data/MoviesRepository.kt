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
        emitSource(handleRequestMovies())
    }

    private suspend fun handleRequestMovies(): LiveData<List<Movie>>{
        val isDbEmpty = localDataSource.count() == 0

        if(isDbEmpty){
            try {
                val responseRemote = remoteDataSource.getMovies()

                if(responseRemote.isSuccessful){
                    if(responseRemote.body() != null) localDataSource.insertAll(responseRemote.body()!!.movies)
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }

        return localDataSource.getMoviesFromDb()
    }

    suspend fun updateMovie(movie: Movie){
        localDataSource.updateMovie(movie)
    }
}