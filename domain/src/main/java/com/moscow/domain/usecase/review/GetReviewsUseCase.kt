package com.moscow.domain.usecase.review

import com.moscow.domain.model.Review
import com.moscow.domain.repository.MovieRepository
import com.moscow.domain.repository.SeriesRepository
import javax.inject.Inject

class GetReviewsUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(
        id: Int,
        page: Int,
        isMovie: Boolean
    ): List<Review> =
        if (isMovie) {
            movieRepository.getReviewsMovie(
                id = id,
                page = page
            )
        } else {
            seriesRepository.getSeriesReviews(
                id = id,
                page = page
            )
        }
}