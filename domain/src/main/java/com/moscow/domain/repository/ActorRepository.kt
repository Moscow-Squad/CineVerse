package com.moscow.domain.repository

import com.moscow.domain.model.ActorDetails
import com.moscow.domain.model.Movie

interface ActorRepository {
    suspend fun getActorDetails(actorId: Int): ActorDetails
    suspend fun getActorGallery(actorId: Int): List<String>
    suspend fun getBestOfMovies(actorId: Int): List<Movie>
}