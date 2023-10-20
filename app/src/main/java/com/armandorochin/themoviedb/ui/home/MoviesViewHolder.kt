package com.armandorochin.themoviedb.ui.home

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView.GONE
import androidx.recyclerview.widget.RecyclerView.VISIBLE
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.armandorochin.themoviedb.databinding.ItemMovieBinding
import com.armandorochin.themoviedb.domain.model.Movie
import com.bumptech.glide.Glide


class MoviesViewHolder (view: View) : ViewHolder(view) {

    private val binding = ItemMovieBinding.bind(view)
    fun render(movie: Movie, onClickListener: (Movie) -> Unit){
        binding.tvMovieTitle.text = movie.title
        Glide.with(binding.ivMoviePoster.context).load("https://image.tmdb.org/t/p/w185/${movie.posterPath}").into(binding.ivMoviePoster)
        itemView.setOnClickListener { onClickListener(movie) }
        if (movie.favorite) binding.ivFav.visibility = VISIBLE else binding.ivFav.visibility = GONE
    }
}