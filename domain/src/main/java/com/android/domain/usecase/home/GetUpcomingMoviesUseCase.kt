package com.android.domain.usecase.home

import com.moscow.domain.repository.HomeRepository
import com.moscow.domain.model.Movie

class GetUpcomingMoviesUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(page:Int): List<Movie> =
        homeRepository.getUpComingMovies(page)
}