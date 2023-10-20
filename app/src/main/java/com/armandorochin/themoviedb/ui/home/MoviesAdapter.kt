package com.armandorochin.themoviedb.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.armandorochin.themoviedb.R
import com.armandorochin.themoviedb.domain.model.Movie

class MoviesAdapter(private val moviesList:List<Movie>,  private val onClickListener: (Movie) -> Unit) : RecyclerView.Adapter<MoviesViewHolder>() {
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