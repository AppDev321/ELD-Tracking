package com.dopsi.webapp.data.repo.movie

import com.dopsi.webapp.data.model.MovieResponse
import com.dopsi.webapp.data.service.ApiServiceInterface
import retrofit2.Response
import javax.inject.Inject

class MovieRepoImpl @Inject constructor(private var apiServiceInterface: ApiServiceInterface) : MovieRepo {
    override suspend fun getMoviesList(): Response<MovieResponse> {
        return  apiServiceInterface.getMoviesList()
    }
}