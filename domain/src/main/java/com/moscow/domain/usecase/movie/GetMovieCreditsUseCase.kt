package com.moscow.domain.usecase.movie

import com.moscow.domain.model.CreditsDetails
import com.moscow.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieCreditsUseCase @Inject constructor(
    private val detailsRepository: MovieRepository
) {
    suspend operator fun invoke(id:Int): CreditsDetails =
       detailsRepository.getMovieCredits(id)
}