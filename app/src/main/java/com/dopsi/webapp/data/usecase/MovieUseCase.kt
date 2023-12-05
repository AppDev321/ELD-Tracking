package com.dopsi.webapp.data.usecase

import com.dopsi.webapp.data.model.MovieResponse
import com.dopsi.webapp.data.repo.movie.MovieRepo
import com.dopsi.webapp.domain.usecase.NetworkUseCaseHelper
import com.dopsi.webapp.extensions.empty
import retrofit2.Response
import javax.inject.Inject

class MovieUseCase @Inject constructor(
    private val movieRepo: MovieRepo,
) : NetworkUseCaseHelper<MovieResponse, MovieUseCase.Params>() {

    override suspend fun buildUseCase(params: Params?): Response<MovieResponse> {
        return movieRepo.getMoviesList()
    }

    data class Params constructor(val params: String) {
        companion object {
            fun create(packId: String) = Params(packId)
        }
    }
}