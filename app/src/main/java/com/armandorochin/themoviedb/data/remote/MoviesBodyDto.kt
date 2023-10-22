package com.armandorochin.themoviedb.data.remote

import com.google.gson.annotations.SerializedName

data class MoviesBodyDto(
    val page: Int,
    @SerializedName("results")
    val movies: List<MovieDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)