package com.moscow.domain.usecase.actor

import com.moscow.domain.model.Movie
import com.moscow.domain.repository.ActorRepository

class GetActorBestMoviesUseCase(
    private val actorRepository: ActorRepository
) {
    suspend operator fun invoke(actorId: Int): List<Movie> =
        actorRepository.getBestOfMovies(actorId)
}