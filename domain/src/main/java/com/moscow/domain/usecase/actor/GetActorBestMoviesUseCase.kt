package com.moscow.domain.usecase.actor

import com.moscow.domain.model.Movie
import com.moscow.domain.repository.ActorRepository
import javax.inject.Inject

class GetActorBestMoviesUseCase @Inject constructor(
    private val actorRepository: ActorRepository
) {
    suspend operator fun invoke(actorId: Int): List<Movie> =
        actorRepository.getBestOfMovies(actorId).distinctBy { it.id }
}