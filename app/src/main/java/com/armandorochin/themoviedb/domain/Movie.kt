package com.armandorochin.themoviedb.domain

import com.armandorochin.themoviedb.data.local.MovieLocal

data class Movie(
    val movieId: Int,
    val genreIds: List<Int>,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
    val favorite: Boolean
)

fun Movie.toMovieLocal() = MovieLocal(
    movieId = movieId,
    genreIds = genreIds,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    voteAverage = voteAverage,
    favorite = favorite
)