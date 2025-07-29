package com.moscow.domain.usecase.genre

import com.moscow.domain.repository.GenreRepository
import javax.inject.Inject

class GenreUseCase @Inject constructor(
    private val genreRepository: GenreRepository
) {
    suspend fun getMoviesGenres() =
        genreRepository.getMoviesGenres()

    suspend fun getSeriesGenres() =
        genreRepository.getSeriesGenres()
}