package com.android.domain.usecase.home

import com.android.domain.model.Movie
import com.android.domain.repository.HomeRepository

class GetUpcomingMoviesUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(page:Int): List<Movie> =
        homeRepository.getUpComingMovies(page)
}