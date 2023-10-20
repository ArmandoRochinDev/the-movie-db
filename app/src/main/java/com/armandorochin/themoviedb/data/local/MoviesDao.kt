package com.armandorochin.themoviedb.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MoviesDao{
    @Query("SELECT * FROM MovieLocal")
    fun getMovies(): LiveData<List<MovieLocal>>

    @Insert
    suspend fun insertMovie(movie: MovieLocal)

    @Insert
    suspend fun insertAll(movies: List<MovieLocal>)
    @Update
    suspend fun updateMovie(movie: MovieLocal)

    @Query("SELECT COUNT(*) FROM MovieLocal")
    suspend fun count():Int
}