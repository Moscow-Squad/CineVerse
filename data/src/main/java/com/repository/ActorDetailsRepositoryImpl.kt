package com.repository

import com.android.domain.model.ActorDetails
import com.android.domain.model.Movie
import com.android.domain.repository.ActorDetailsRepository
import com.data_source.remote.ActorDetailsRemoteDataSource
import com.mapper.toDomain

class ActorDetailsRepositoryImpl(
    private val actorDetailsRemoteDataSource: ActorDetailsRemoteDataSource,
) : ActorDetailsRepository {

    override suspend fun getActorDetails(actorId: Int): ActorDetails {
        val socialMedia = actorDetailsRemoteDataSource.getSocialMedia(actorId)

        return actorDetailsRemoteDataSource.getActorDetails(actorId).toDomain(
            youtubeLink = socialMedia.youtubeId ?: "",
            facebookLink = socialMedia.facebookId ?: "",
            instagramLink = socialMedia.instagramId ?: ""
        )
    }


    override suspend fun getActorGallery(actorId: Int) =
        actorDetailsRemoteDataSource.getGallery(actorId).images.map { it.toDomain() }


    override suspend fun getBestOfMovies(actorId: Int): List<Movie> {

        val bestMovies = actorDetailsRemoteDataSource.getBestOfMovies(actorId)
        val bestAsCast = bestMovies.cast.mapNotNull { runCatching { it.toDomain() }.getOrNull() }
        val bestAsCrew = bestMovies.crew.mapNotNull { runCatching { it.toDomain() }.getOrNull() }
        return (bestAsCast + bestAsCrew).sortedByDescending { it.rating }
    }

}


