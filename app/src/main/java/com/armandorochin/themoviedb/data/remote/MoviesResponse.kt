package com.armandorochin.themoviedb.data.remote

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    val page: Int,
    val movies: List<MovieRemote>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)