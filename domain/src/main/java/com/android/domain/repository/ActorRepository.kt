package com.android.domain.repository

import com.android.domain.model.ActorDetails
import com.android.domain.model.Movie

interface ActorRepository {
    suspend fun getActorDetails(actorId: Int): ActorDetails
    suspend fun getActorGallery(actorId: Int): List<String>
    suspend fun getBestOfMovies(actorId: Int): List<Movie>
}