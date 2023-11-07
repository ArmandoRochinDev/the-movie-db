package com.armandorochin.themoviedb.ui.screens.discovery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.armandorochin.themoviedb.databinding.ItemMovieBinding
import com.armandorochin.themoviedb.di.NetworkModule.IMAGEURL_185
import com.armandorochin.themoviedb.domain.model.Movie
import com.bumptech.glide.Glide

class DiscoveryAdapter(private val onClickListener:(Movie) -> Unit) : PagingDataAdapter<Movie, DiscoveryMovieViewHolder>(MovieDiffCallBack()){
    override fun onBindViewHolder(holder: DiscoveryMovieViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListener)
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

    fun bind(movie: Movie?, onClickListener:(Movie) -> Unit) {
        Glide.with(binding.ivMoviePoster.context).load("${IMAGEURL_185}${movie?.posterPath}").into(binding.ivMoviePoster)

        itemView.setOnClickListener { onClickListener(movie!!) }
    }
}