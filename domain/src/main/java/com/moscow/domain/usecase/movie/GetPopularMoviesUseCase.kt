package com.moscow.domain.usecase.movie

import com.moscow.domain.repository.MovieRepository

class GetPopularMoviesUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(page: Int) = repository.getPopularMovies(page)
}