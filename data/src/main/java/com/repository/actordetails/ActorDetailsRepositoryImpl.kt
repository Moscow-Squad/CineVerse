package com.repository.actordetails

import com.android.domain.model.ActorDetails
import com.android.domain.model.Movie
import com.android.domain.repository.ActorDetailsRepository
import com.mapper.toDomain
import com.remote.source.ActorDetailsRemoteDataSource
import com.utils.BaseRepository

class ActorDetailsRepositoryImpl(
    private val actorDetailsRemoteDataSource: ActorDetailsRemoteDataSource,
) : ActorDetailsRepository, BaseRepository() {

    override suspend fun getActorDetails(actorId: Int) = tryToExecute {
            val socialMedia = actorDetailsRemoteDataSource.getSocialMedia(actorId)
            actorDetailsRemoteDataSource.getActorDetails(actorId).toDomain(
                youtubeLink = socialMedia.youtubeId ?: "",
                facebookLink = socialMedia.facebookId ?: "",
                instagramLink = socialMedia.instagramId ?: ""
            )
    }

    override suspend fun getGallery(actorId: Int) =  tryToExecute {
              actorDetailsRemoteDataSource.getGallery(actorId).profiles.map { it.toDomain() }
    }

    override suspend fun getBestOfMovies(actorId: Int): List<Movie>  =  tryToExecute {

        val bestMovies = actorDetailsRemoteDataSource.getBestOfMovies(actorId)
        val bestAsCast = bestMovies.cast.mapNotNull { runCatching { it.toDomain() }.getOrNull() }
        val bestAsCrew = bestMovies.crew.mapNotNull { runCatching { it.toDomain() }.getOrNull() }
        (bestAsCast + bestAsCrew).sortedByDescending { it.rating }
    }
}