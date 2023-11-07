package com.armandorochin.themoviedb.data.local.movie

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface MoviesDao{
    @Insert
    suspend fun insertAll(movies: List<MovieLocal>)

    @Query("SELECT COUNT(*) FROM MovieLocal")
    suspend fun count():Int

    @Query("DELETE FROM MovieLocal")
    suspend fun deleteAllDiscoveryMovies()

    @Query("SELECT * FROM MovieLocal ORDER BY createdAt DESC LIMIT 1")
    suspend fun getLastCreatedMovie() : MovieLocal
    @Query("SELECT * FROM MovieLocal ORDER BY createdAt ASC LIMIT 1")
    suspend fun getFirstCreatedMovie() : MovieLocal

    //Paging
    @Query("SELECT * FROM MovieLocal ORDER BY popularity DESC")
    fun getDiscoveryMovies(): PagingSource<Int, MovieLocal>

    @Transaction
    suspend fun deleteAllAndInsertTransaction(movies: List<MovieLocal>){
        deleteAllDiscoveryMovies()
        insertAll(movies)
    }

    @Query("SELECT * FROM MovieLocal WHERE movieId = :movieId")
    fun getMovie(movieId: Int) : LiveData<MovieLocal>
}