package com.armandorochin.themoviedb.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MoviesDao{
    @Query("SELECT * FROM MovieLocal")
    suspend fun getAll(): List<MovieLocal>

    @Insert
    suspend fun insert(movie: MovieLocal)

    @Insert
    suspend fun insertAll(movies: List<MovieLocal>)
    @Update
    suspend fun update(movie: MovieLocal)

    @Query("SELECT COUNT(*) FROM MovieLocal")
    suspend fun count():Int

    @Query("DELETE FROM MovieLocal")
    suspend fun deleteAll()
}