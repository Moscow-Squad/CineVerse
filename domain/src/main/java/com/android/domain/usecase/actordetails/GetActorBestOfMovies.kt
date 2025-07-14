package com.android.domain.usecase.actordetails

import com.android.domain.exception.CineVerseException
import com.android.domain.model.Movie
import com.android.domain.repository.ActorDetailsRepository
import kotlinx.coroutines.flow.Flow

class GetActorBestOfMovies(private val actorDetailsRepository: ActorDetailsRepository) {
    suspend fun getActorBestOfMovies(actorId: Int): Flow<List<Movie>> {
        return actorDetailsRepository.getBestOfMovies(actorId)
    }
}