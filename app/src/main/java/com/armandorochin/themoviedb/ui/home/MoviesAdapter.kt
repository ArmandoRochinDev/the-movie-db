package com.armandorochin.themoviedb.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.armandorochin.themoviedb.R
import com.armandorochin.themoviedb.databinding.ItemMovieBinding
import com.armandorochin.themoviedb.domain.model.Movie
import com.bumptech.glide.Glide

class MoviesAdapter(private var moviesList:List<Movie>, private val onClickListener: (Movie) -> Unit) : RecyclerView.Adapter<MoviesViewHolder>() {

    fun updateList(newList: List<Movie>){
        val moviesDiff = MoviesDiffUtil(moviesList, newList)
        val result = DiffUtil.calculateDiff(moviesDiff)

        moviesList = newList
        result.dispatchUpdatesTo(this)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MoviesViewHolder(layoutInflater.inflate(R.layout.item_movie, parent, false))
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val currentMovie = moviesList[position]
        holder.render(currentMovie, onClickListener)
    }

    override fun getItemCount(): Int = moviesList.size
}

class MoviesViewHolder (view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemMovieBinding.bind(view)
    fun render(movie: Movie, onClickListener: (Movie) -> Unit){
        Glide.with(binding.ivMoviePoster.context).load("https://image.tmdb.org/t/p/w185/${movie.posterPath}").into(binding.ivMoviePoster)
        itemView.setOnClickListener { onClickListener(movie) }
        if (movie.favorite) binding.ivFav.visibility = RecyclerView.VISIBLE else binding.ivFav.visibility =
            RecyclerView.GONE
    }
}