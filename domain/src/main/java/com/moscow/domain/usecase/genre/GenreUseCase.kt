package com.moscow.domain.usecase.genre

import com.moscow.domain.repository.GenreRepository

class GenreUseCase(
    private val genreRepository: GenreRepository
) {
    suspend fun getMoviesGenres() =
        genreRepository.getMoviesGenres()

    suspend fun getSeriesGenres() =
        genreRepository.getSeriesGenres()
}