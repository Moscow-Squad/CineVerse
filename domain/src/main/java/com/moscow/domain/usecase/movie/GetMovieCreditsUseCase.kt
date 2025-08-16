package com.moscow.domain.usecase.movie

import com.moscow.domain.model.CreditsInfo
import com.moscow.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieCreditsUseCase @Inject constructor(
    private val detailsRepository: MovieRepository
) {
    suspend operator fun invoke(id: Int): CreditsInfo =
       detailsRepository.getCreditsMovie(id = id)
}