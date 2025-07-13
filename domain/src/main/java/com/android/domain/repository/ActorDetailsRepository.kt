package com.android.domain.repository

import com.android.domain.model.Actor
import com.android.domain.model.Movie

interface ActorDetailsRepository {
    fun getActorDetails(actorId: Int): Actor
    fun getGallery(actorId: Int): List<String>
    fun getBestOfMovies(actorId: Int): List<Movie>
}