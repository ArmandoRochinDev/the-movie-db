package com.armandorochin.themoviedb.ui.screens.discovery

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.armandorochin.themoviedb.R
import com.armandorochin.themoviedb.databinding.ItemMovieBinding
import com.armandorochin.themoviedb.di.NetworkModule.IMAGEURL_500
import com.armandorochin.themoviedb.domain.model.Movie
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.transition.Transition

class DiscoveryAdapter(private val isFromMainFragment:Boolean, private val onClickListener:(Movie) -> Unit) : PagingDataAdapter<Movie, DiscoveryMovieViewHolder>(MovieDiffCallBack()){
    override fun onBindViewHolder(holder: DiscoveryMovieViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoveryMovieViewHolder {
        return DiscoveryMovieViewHolder(
            isFromMainFragment,
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
}

class DiscoveryMovieViewHolder(
    private val isBigItem:Boolean,
    private val binding: ItemMovieBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie?, onClickListener:(Movie) -> Unit) {

        binding.root.layoutParams.width = if(isBigItem) 440 else binding.root.layoutParams.width
        val imageUrl = if(isBigItem) movie?.backdropPath else movie?.posterPath

        binding.tvTitle.text = movie?.title ?: ""
        itemView.setOnClickListener { onClickListener(movie!!) }

        Glide.with(binding.ivMoviePoster.context)
            .asBitmap()
            .load("${IMAGEURL_500}$imageUrl")
            .apply(RequestOptions().centerCrop())
            .into(object : BitmapImageViewTarget(binding.ivMoviePoster) {
                override fun onResourceReady(bitmap: Bitmap, transition: Transition<in Bitmap>?) {
                    super.onResourceReady(bitmap, transition)
                    Palette.from(bitmap).generate { palette ->
                        val color = palette!!.getVibrantColor(
                            ContextCompat.getColor(
                                binding.ivMoviePoster.context,
                                R.color.black_translucent_60
                            )
                        )

                        binding.titleBackground.setBackgroundColor(color)
                    }
                }
            })
    }
}