package com.android.domain.usecase.actordetails

import com.android.domain.model.Movie
import com.android.domain.repository.ActorDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetActorBestOfMovies(private val actorDetailsRepository: ActorDetailsRepository) {
    suspend fun getActorBestOfMovies(actorId: Int): List<Movie> {
        return actorDetailsRepository.getBestOfMovies(actorId)
    }
}