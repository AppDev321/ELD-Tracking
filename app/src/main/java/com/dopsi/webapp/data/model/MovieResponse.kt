package com.dopsi.webapp.data.model


data class MovieResponse(
    val page: Int?,
    val results: List<Results?>?
) {
    data class Results(
        val id: Long?=0,
        val original_title: String?="",
        val overview: String?="",
        val poster_path: String?="",
        val vote_average:String?=""
    )
}