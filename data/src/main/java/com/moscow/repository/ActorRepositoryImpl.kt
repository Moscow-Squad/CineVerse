package com.moscow.repository

import com.moscow.data_source.remote.ActorRemoteDataSource
import com.moscow.domain.model.Actor
import com.moscow.domain.model.Movie
import com.moscow.domain.repository.ActorRepository
import com.moscow.mapper.toDomain
import javax.inject.Inject

class ActorRepositoryImpl @Inject constructor(
    private val actorRemoteDataSource: ActorRemoteDataSource,
) : ActorRepository {

    override suspend fun getActorDetails(actorId: Int): Actor {
        val socialMedia = actorRemoteDataSource.getActorSocialMedia(actorId)

        return actorRemoteDataSource.getActorDetails(actorId).toDomain(
            youtubeLink = socialMedia.youtubeId ?: "",
            facebookLink = socialMedia.facebookId ?: "",
            instagramLink = socialMedia.instagramId ?: ""
        )
    }

    override suspend fun getActorGalleryUrl(actorId: Int): List<String> =
        actorRemoteDataSource.getActorGallery(actorId).images.map { it.toDomain() }

    override suspend fun getBestOfMovies(actorId: Int): List<Movie> {
        val bestMovies = actorRemoteDataSource.getActorBestMovies(actorId)
        val bestMoviesActorAsCast = bestMovies.cast.mapNotNull {
            runCatching { it.toDomain() }.getOrNull()
        }
        val bestMoviesActorAsCrew = bestMovies.crew.mapNotNull {
            runCatching { it.toDomain() }.getOrNull()
        }
        return bestMoviesActorAsCast + bestMoviesActorAsCrew
    }
}