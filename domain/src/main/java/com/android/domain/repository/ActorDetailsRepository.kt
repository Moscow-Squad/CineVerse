package com.android.domain.repository

import com.android.domain.model.ActorDetails
import com.android.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface ActorDetailsRepository {
    suspend fun getActorDetails(actorId: Int): ActorDetails
    suspend fun getGallery(actorId: Int): Flow<List<String>>
    suspend fun getBestOfMovies(actorId: Int): Flow<List<Movie>>
}