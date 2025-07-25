package com.moscow.domain.usecase.review

import com.moscow.domain.model.Review
import com.moscow.domain.repository.MovieRepository
import com.moscow.domain.repository.SeriesRepository

class GetReviewsUseCase(
    private val movieRepository: MovieRepository,
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(id: Int, page: Int, isMovie: Boolean): List<Review> =
        if (isMovie)
            movieRepository.getMovieReviews(id, page)
        else
            seriesRepository.getSeriesReviews(id, page)
}