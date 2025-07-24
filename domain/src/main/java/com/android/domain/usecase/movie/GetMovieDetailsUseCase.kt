package com.android.domain.usecase.movie

import com.android.domain.model.details.MovieDetail
import com.android.domain.repository.MovieRepository

class GetMovieDetailsUseCase(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(id: Int): MovieDetail {
        return movieRepository.getMoviesDetail(id)
    }
}