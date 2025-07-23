package com.android.domain.usecase.movie

import com.android.domain.repository.MovieRepository

class GetMovieByGenreIdUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(genreId: Int, page: Int) =
        movieRepository.getMoviesByGenreId(genreId, page)
}