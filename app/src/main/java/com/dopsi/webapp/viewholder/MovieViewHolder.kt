package com.dopsi.webapp.viewholder

import com.core.base.BaseViewHolder
import com.core.data.model.MovieResponse
import com.dopsi.webapp.databinding.ItemMovieBinding

class MovieViewHolder (
    private var itemMovieBinding: ItemMovieBinding
) : BaseViewHolder<MovieResponse.Results>(itemMovieBinding.root) {

    override fun bindItem(item: MovieResponse.Results) {
        itemMovieBinding.txtName.text = item.original_title
        val context = itemMovieBinding.root.context

    }
}