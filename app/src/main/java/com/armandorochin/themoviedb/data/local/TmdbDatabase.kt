package com.armandorochin.themoviedb.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.armandorochin.themoviedb.data.local.movie.Converters
import com.armandorochin.themoviedb.data.local.movie.MovieLocal
import com.armandorochin.themoviedb.data.local.movie.MoviesDao
import com.armandorochin.themoviedb.domain.model.Movie

@TypeConverters(Converters::class)
@Database(entities = [MovieLocal::class], version = 1, exportSchema = false)
abstract class TmdbDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}