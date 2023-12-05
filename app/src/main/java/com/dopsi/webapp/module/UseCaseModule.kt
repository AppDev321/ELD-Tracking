package com.dopsi.webapp.module

import com.dopsi.webapp.data.repo.movie.MovieRepo
import com.dopsi.webapp.data.usecase.MovieUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideMovieUseCase(movieRepository: MovieRepo) = MovieUseCase(movieRepository)

}
