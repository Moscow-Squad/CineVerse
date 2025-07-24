package com.android.domain.usecase.movie

import com.android.domain.repository.MovieRepository

class GetPopularMoviesUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(page: Int) = repository.getPopularMovies(page)
}