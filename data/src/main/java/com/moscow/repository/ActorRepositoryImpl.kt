package com.moscow.repository

import com.moscow.data_source.remote.ActorRemoteDataSource
import com.moscow.domain.model.ActorDetails
import com.moscow.domain.model.Movie
import com.moscow.domain.repository.ActorRepository
import com.moscow.mapper.toDomain

class ActorRepositoryImpl(
    private val actorRemoteDataSource: ActorRemoteDataSource,
) : ActorRepository {

    override suspend fun getActorDetails(actorId: Int): ActorDetails {
        val socialMedia = actorRemoteDataSource.getActorSocialMedia(actorId)

        return actorRemoteDataSource.getActorDetails(actorId).toDomain(
            youtubeLink = socialMedia.youtubeId ?: "",
            facebookLink = socialMedia.facebookId ?: "",
            instagramLink = socialMedia.instagramId ?: ""
        )
    }

    override suspend fun getActorGallery(actorId: Int) =
        actorRemoteDataSource.getActorGallery(actorId).images.map { it.toDomain() }

    override suspend fun getBestOfMovies(actorId: Int): List<Movie> {
        val bestMovies = actorRemoteDataSource.getActorBestMovies(actorId)
        val bestAsCast = bestMovies.cast.mapNotNull { runCatching { it.toDomain() }.getOrNull() }
        val bestAsCrew = bestMovies.crew.mapNotNull { runCatching { it.toDomain() }.getOrNull() }
        return (bestAsCast + bestAsCrew).sortedByDescending { it.rating }
    }

}
