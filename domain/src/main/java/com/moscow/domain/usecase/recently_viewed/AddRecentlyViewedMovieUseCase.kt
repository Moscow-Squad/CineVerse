package com.moscow.domain.usecase.recently_viewed

import com.moscow.domain.model.Movie
import com.moscow.domain.repository.RecentlyViewedRepository
import javax.inject.Inject

class AddRecentlyViewedMovieUseCase @Inject constructor(
    private val repository: RecentlyViewedRepository
) {
    suspend operator fun invoke(
        movie: Movie
    ) = repository.addRecentlyViewedMovie(movie = movie)

}