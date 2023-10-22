package com.armandorochin.themoviedb.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.armandorochin.themoviedb.domain.model.Movie
import java.util.Date

@Entity
data class MovieLocal(
    @PrimaryKey (autoGenerate = true) val movieId: Int,
    val genreIds: List<Int>,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
    val favorite: Boolean = false,
    val createdAt: Date
)

fun MovieLocal.toMovie() = Movie(
    movieId = movieId,
    genreIds = genreIds,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    voteAverage = voteAverage,
    favorite = favorite,
    createdAt = createdAt
)