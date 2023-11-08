package com.armandorochin.themoviedb.data.local.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.armandorochin.themoviedb.domain.model.Movie
import java.util.Date

@Entity
data class MovieLocal(
    @PrimaryKey(autoGenerate = true)
    val uid: Int?,
    val adult: Boolean,
    val backdropPath: String?,
    val genreIds: List<Int>,
    val movieId: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String?,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
    var page: Int,
    var createdAt: Date
)

fun MovieLocal.toMovie() = Movie(
    uid,
    adult,
    backdropPath,
    genreIds,
    movieId,
    originalLanguage,
    originalTitle,
    overview,
    popularity,
    posterPath,
    releaseDate,
    title,
    video,
    voteAverage,
    voteCount,
    page,
    createdAt
)