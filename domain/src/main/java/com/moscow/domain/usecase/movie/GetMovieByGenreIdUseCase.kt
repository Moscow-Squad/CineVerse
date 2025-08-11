package com.moscow.domain.usecase.movie

import com.moscow.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieByGenreIdUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(
        genreId: Int,
        page: Int
    ) = movieRepository.getMoviesByGenreId(
        genreId = genreId,
        page = page
    ).distinctBy { it.id }
}