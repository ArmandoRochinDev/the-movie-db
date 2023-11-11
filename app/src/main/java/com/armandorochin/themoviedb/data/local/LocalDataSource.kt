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

    suspend fun count(category: String): Int{
        return dao.count(category)
    }

    fun getMovies(category: String): PagingSource<Int, MovieLocal>{
        return dao.getMovies(category)
    }

    fun getMovie(movieId:Int): LiveData<MovieLocal>{
        return dao.getMovie(movieId)
    }

    suspend fun getLastCreatedMovie(category: String):MovieLocal{
        return dao.getLastCreatedMovie(category)
    }
    suspend fun getFirstCreatedMovie(category: String):MovieLocal{
        return dao.getFirstCreatedMovie(category)
    }

    suspend fun insertAndDeleteTransaction(movies: List<MovieLocal>, category: String){
        dao.deleteAllAndInsertTransaction(movies, category)
    }
}