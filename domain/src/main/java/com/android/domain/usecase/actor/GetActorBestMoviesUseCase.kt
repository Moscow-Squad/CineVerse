package com.android.domain.usecase.actor

import com.android.domain.model.Movie
import com.android.domain.repository.ActorRepository

class GetActorBestMoviesUseCase(private val actorRepository: ActorRepository) {
    suspend operator fun invoke(actorId: Int): List<Movie> =
        actorRepository.getBestOfMovies(actorId)
}