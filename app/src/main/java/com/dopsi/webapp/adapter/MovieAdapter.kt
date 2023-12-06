package com.dopsi.webapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.core.base.BaseRecyclerAdapter
import com.core.data.model.MovieResponse
import com.core.interfaces.ItemClickListener
import com.dopsi.webapp.databinding.ItemMovieBinding
import com.dopsi.webapp.viewholder.MovieViewHolder

class MovieAdapter(private var itemClickListener: ItemClickListener) :
    BaseRecyclerAdapter<MovieResponse.Results>(itemClickListener) {

    override fun createBaseViewHolder(parent: ViewGroup, viewType: Int) =

        MovieViewHolder(
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
}