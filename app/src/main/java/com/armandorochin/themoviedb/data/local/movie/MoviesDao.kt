package com.armandorochin.themoviedb.data.local.movie

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface MoviesDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieLocal>)

    @Query("SELECT COUNT(*) FROM MovieLocal WHERE category = :category")
    suspend fun count(category: String):Int

    @Query("DELETE FROM MovieLocal WHERE category = :category")
    suspend fun deleteAllMovies(category: String)

    @Query("SELECT * FROM MovieLocal WHERE category = :category ORDER BY createdAt DESC LIMIT 1")
    suspend fun getLastCreatedMovie(category: String) : MovieLocal
    @Query("SELECT * FROM MovieLocal WHERE category = :category ORDER BY createdAt ASC LIMIT 1")
    suspend fun getFirstCreatedMovie(category: String) : MovieLocal

    //Paging
    //@Query("SELECT * FROM MovieLocal ORDER BY popularity DESC")
    @Query("SELECT * FROM MovieLocal WHERE category = :category")
    fun getMovies(category: String): PagingSource<Int, MovieLocal>

    @Transaction
    suspend fun deleteAllAndInsertTransaction(movies: List<MovieLocal>, category: String){
        deleteAllMovies(category)
        insertAll(movies)
    }

    @Query("SELECT * FROM MovieLocal WHERE movieId = :movieId")
    fun getMovie(movieId: Int) : LiveData<MovieLocal>
}