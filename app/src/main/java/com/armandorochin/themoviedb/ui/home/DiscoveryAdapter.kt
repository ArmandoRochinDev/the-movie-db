package com.armandorochin.themoviedb.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.armandorochin.themoviedb.databinding.ItemMovieBinding
import com.armandorochin.themoviedb.di.NetworkModule.IMAGEURL
import com.armandorochin.themoviedb.domain.model.Movie
import com.bumptech.glide.Glide

class DiscoveryAdapter : PagingDataAdapter<Movie, DiscoveryMovieViewHolder>(MovieDiffCallBack()){
    override fun onBindViewHolder(holder: DiscoveryMovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoveryMovieViewHolder {
        return DiscoveryMovieViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
}

class DiscoveryMovieViewHolder(
    private val binding: ItemMovieBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie?) {
        Glide.with(binding.ivMoviePoster.context).load("${IMAGEURL}${movie?.posterPath}").into(binding.ivMoviePoster)
    }
}