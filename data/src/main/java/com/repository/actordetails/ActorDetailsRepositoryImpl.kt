package com.repository.actordetails

import com.android.domain.model.ActorDetails
import com.android.domain.model.Movie
import com.android.domain.repository.ActorDetailsRepository
import com.mapper.toDomain
import com.remote.data_source.ActorDetailsRemoteDataSourceImpl

class ActorDetailsRepositoryImpl(
    private val actorDetailsRemoteDataSourceImpl: ActorDetailsRemoteDataSourceImpl,
) : ActorDetailsRepository {

    override suspend fun getActorDetails(actorId: Int): ActorDetails {
        val socialMedia = actorDetailsRemoteDataSourceImpl.getSocialMedia(actorId)

        return actorDetailsRemoteDataSourceImpl.getActorDetails(actorId).toDomain(
            youtubeLink = socialMedia.youtubeId ?: "",
            facebookLink = socialMedia.facebookId ?: "",
            instagramLink = socialMedia.instagramId ?: ""

        )
    }


    override suspend fun getGallery(actorId: Int) =
        actorDetailsRemoteDataSourceImpl.getGallery(actorId).profiles.map { it.toDomain() }


    override suspend fun getBestOfMovies(actorId: Int): List<Movie> {

        val bestMovies = actorDetailsRemoteDataSourceImpl.getBestOfMovies(actorId)
        val bestAsCast = bestMovies.cast.mapNotNull { runCatching { it.toDomain() }.getOrNull() }
        val bestAsCrew = bestMovies.crew.mapNotNull { runCatching { it.toDomain() }.getOrNull() }
        return (bestAsCast + bestAsCrew).sortedByDescending { it.rating }
    }

}


