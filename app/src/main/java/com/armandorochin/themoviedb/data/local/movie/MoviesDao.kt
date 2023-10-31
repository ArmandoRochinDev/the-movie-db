package com.armandorochin.themoviedb.data.local.movie

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.armandorochin.themoviedb.domain.model.Movie

@Dao
interface MoviesDao{
    @Insert
    suspend fun insertAll(movies: List<Movie>)

    @Query("SELECT COUNT(*) FROM Movie")
    suspend fun count():Int

    @Query("SELECT * FROM Movie ORDER BY createdAt DESC LIMIT 1")
    suspend fun getLastCreatedMovie() : Movie
    @Query("SELECT * FROM Movie ORDER BY createdAt ASC LIMIT 1")
    suspend fun getFirstCreatedMovie() : Movie

    //Paging
    @Query("SELECT * FROM Movie ORDER BY popularity DESC")
    fun getDiscoveryMovies(): PagingSource<Int, Movie>
}