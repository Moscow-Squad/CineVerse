package com.moscow.domain.usecase.home

import com.moscow.domain.repository.HomeRepository
import com.moscow.domain.model.Movie
import javax.inject.Inject

class GetUpcomingMoviesUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(page:Int, forceRefresh: Boolean = false): List<Movie> =
        homeRepository.getUpComingMovies(page, forceRefresh)
}