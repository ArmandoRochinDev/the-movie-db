package com.armandorochin.themoviedb.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@TypeConverters(Converters::class)
@Database(entities = [MovieLocal::class], version = 1)
abstract class TmdbDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}