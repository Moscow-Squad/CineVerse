package com.repository.actordetails

import com.android.domain.model.ActorDetails
import com.android.domain.model.Movie
import com.android.domain.repository.ActorDetailsRepository
import com.remote.actordetails.ActorDetailsRemoteDataSource

class ActorDetailsRepositoryImpl(
    private val actorDetailsRemoteDataSource: ActorDetailsRemoteDataSource
) : ActorDetailsRepository {
    override fun getActorDetails(actorId: Int): ActorDetails {
        TODO("Not yet implemented")
    }

    override fun getGallery(actorId: Int): List<String> {
        TODO("Not yet implemented")
    }

    override fun getBestOfMovies(actorId: Int): List<Movie> {
        TODO("Not yet implemented")
    }
}