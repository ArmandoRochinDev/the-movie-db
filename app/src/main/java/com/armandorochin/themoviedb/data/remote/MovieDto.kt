package com.armandorochin.themoviedb.data.remote

import androidx.room.PrimaryKey
import com.armandorochin.themoviedb.data.local.movie.MovieLocal
import com.google.gson.annotations.SerializedName
import java.util.Date

data class MovieDto (
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("genre_ids")
    val genreIds: List<Int>,
    @SerializedName("id")
    @PrimaryKey val movieId: Int,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int,
    var page: Int,
    var createdAt: Date
)

fun MovieDto.toMovieLocal(pageIndex: Int) = MovieLocal(
    null,
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
    pageIndex,
    Date()
)