package com.android.domain.usecase.genre

import com.android.domain.repository.GenreRepository

class GenreUseCase(
    private val genreRepository: GenreRepository
) {
    suspend fun getMoviesGenres() =
        genreRepository.getMoviesGenres()

    suspend fun getSeriesGenres() =
        genreRepository.getSeriesGenres()
}