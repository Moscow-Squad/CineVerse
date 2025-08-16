package com.moscow.domain.usecase.movie

import com.moscow.domain.model.Movie
import com.moscow.domain.repository.MovieRepository
import javax.inject.Inject

class GetUpcomingMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(
        page:Int,
        forceRefresh: Boolean = false
    ): List<Movie> = movieRepository.getUpComingMovies(
        page = page,
        forceRefresh = forceRefresh
    )
}