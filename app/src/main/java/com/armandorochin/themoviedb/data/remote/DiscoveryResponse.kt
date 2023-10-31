package com.armandorochin.themoviedb.data.remote

import com.armandorochin.themoviedb.domain.model.Movie
import com.google.gson.annotations.SerializedName

data class DiscoveryResponse(
    val page: Int,
    @SerializedName("results")
    val movies: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)