package com.armandorochin.themoviedb.domain.model

import java.util.Date

data class Movie(
    val uid: Int?,
    val adult: Boolean,
    val backdropPath: String?,
    val genreIds: List<Int>,
    val movieId: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val releaseDate: String?,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
    var page: Int,
    var createdAt: Date
)