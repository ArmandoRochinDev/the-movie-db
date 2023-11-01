package com.armandorochin.themoviedb.ui.screens.discovery

import androidx.recyclerview.widget.DiffUtil
import com.armandorochin.themoviedb.domain.model.Movie

class MovieDiffCallBack : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.movieId == newItem.movieId
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}