package com.moscow.domain.usecase.movie

import com.moscow.domain.repository.MovieRepository

class GetMovieByGenreIdUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(genreId: Int, page: Int) =
        movieRepository.getMoviesByGenreId(genreId, page)
}