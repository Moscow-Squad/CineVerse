package com.moscow.domain.usecase.movie

import com.moscow.domain.model.details.MovieDetail
import com.moscow.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(id: Int): MovieDetail {
        return movieRepository.getMoviesDetail(id)
    }
}