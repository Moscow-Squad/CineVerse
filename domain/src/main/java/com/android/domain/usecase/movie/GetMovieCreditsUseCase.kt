package com.android.domain.usecase.movie

import com.android.domain.model.CreditsDetails
import com.android.domain.repository.MovieRepository

class GetMovieCreditsUseCase(
    private val detailsRepository: MovieRepository
) {
    suspend operator fun invoke(id:Int): CreditsDetails =
       detailsRepository.getMovieCredits(id)
}