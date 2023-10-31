package com.armandorochin.themoviedb.data.local

import androidx.paging.PagingSource
import androidx.room.RoomDatabase
import com.armandorochin.themoviedb.data.local.movie.MoviesDao
import com.armandorochin.themoviedb.domain.model.Movie
import javax.inject.Inject


class LocalDataSource @Inject constructor(
    private val dao: MoviesDao,
    private val database: TmdbDatabase
){
    suspend fun insertAll(movies: List<Movie>){
        dao.insertAll(movies)
    }

    suspend fun count(): Int{
        return dao.count()
    }

    fun getDiscoveryMovies(): PagingSource<Int, Movie>{
        return dao.getDiscoveryMovies()
    }

    fun getDatabase(): RoomDatabase{
        return database
    }

    suspend fun getLastCreatedMovie():Movie{
        return dao.getLastCreatedMovie()
    }
    suspend fun getFirstCreatedMovie():Movie{
        return dao.getFirstCreatedMovie()
    }
}