package com.moscow.domain.repository

import com.moscow.domain.model.Actor
import com.moscow.domain.model.Movie

interface ActorRepository {
    suspend fun getActorDetails(actorId: Int): Actor
    suspend fun getActorGalleryUrl(actorId: Int): List<String>
    suspend fun getBestOfMovies(actorId: Int): List<Movie>
}