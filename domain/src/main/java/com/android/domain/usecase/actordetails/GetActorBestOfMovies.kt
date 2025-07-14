package com.android.domain.usecase.actordetails

import com.android.domain.exception.CineVerseException
import com.android.domain.model.Movie
import com.android.domain.repository.ActorDetailsRepository

class GetActorBestOfMovies(private val actorDetailsRepository: ActorDetailsRepository) {
    suspend fun getActorBestOfMovies(actorId: Int, page: Int): List<Movie> {
        val movies = actorDetailsRepository.getBestOfMovies(actorId)
        return if (movies.isEmpty()) throw CineVerseException.NoMoviesFoundException else movies
    }
}