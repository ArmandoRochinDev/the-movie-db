package com.armandorochin.themoviedb.ui.home

import androidx.recyclerview.widget.DiffUtil
import com.armandorochin.themoviedb.domain.model.Movie

class MoviesDiffUtil(
    private val oldData:List<Movie>,
    private val newData: List<Movie>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldData.size
    }

    override fun getNewListSize(): Int {
        return newData.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldData[oldItemPosition].movieId == newData[newItemPosition].movieId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldData[oldItemPosition] == newData[newItemPosition]
    }
}