package com.moscow.domain.usecase.home

import com.moscow.domain.repository.HomeRepository
import com.moscow.domain.model.Movie

class GetRecentlyReleasedMoviesUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(page:Int): List<Movie> =
        homeRepository.getRecentlyReleasedMovies(page)
}