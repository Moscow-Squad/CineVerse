package com.android.domain.usecase.review

import com.android.domain.model.Review
import com.android.domain.repository.MovieRepository
import com.android.domain.repository.SeriesRepository

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