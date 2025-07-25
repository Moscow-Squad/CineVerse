package com.moscow.domain.usecase.movie

import com.moscow.domain.model.CreditsDetails
import com.moscow.domain.repository.MovieRepository

class GetMovieCreditsUseCase(
    private val detailsRepository: MovieRepository
) {
    suspend operator fun invoke(id:Int): CreditsDetails =
       detailsRepository.getMovieCredits(id)
}