package com.armandorochin.themoviedb.data.local

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import com.armandorochin.themoviedb.data.local.movie.MovieLocal
import com.armandorochin.themoviedb.data.local.movie.MoviesDao
import javax.inject.Inject


class LocalDataSource @Inject constructor(
    private val dao: MoviesDao
){
    suspend fun insertAll(movies: List<MovieLocal>){
        dao.insertAll(movies)
    }

    suspend fun count(): Int{
        return dao.count()
    }

    fun getMovies(): PagingSource<Int, MovieLocal>{
        return dao.getMovies()
    }

    fun getMovie(movieId:Int): LiveData<MovieLocal>{
        return dao.getMovie(movieId)
    }

    suspend fun getLastCreatedMovie():MovieLocal{
        return dao.getLastCreatedMovie()
    }
    suspend fun getFirstCreatedMovie():MovieLocal{
        return dao.getFirstCreatedMovie()
    }

    suspend fun insertAndDeleteTransaction(movies: List<MovieLocal>){
        dao.deleteAllAndInsertTransaction(movies)
    }
}