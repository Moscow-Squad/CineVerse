package com.repository.actordetails

import com.android.domain.exception.CineVerseException
import com.android.domain.model.ActorDetails
import com.android.domain.model.Movie
import com.android.domain.repository.ActorDetailsRepository
import com.mapper.toDomain
import com.remote.source.ActorDetailsRemoteDataSource
import com.utils.BaseRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ActorDetailsRepositoryImpl(
    private val actorDetailsRemoteDataSource: ActorDetailsRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher,
) : ActorDetailsRepository, BaseRepository() {
    override suspend fun getActorDetails(actorId: Int): ActorDetails =
        tryToExecute(exception = CineVerseException.ActorDetailsNotFoundException) {
            val socialMedia = actorDetailsRemoteDataSource.getSocialMedia(actorId)
            actorDetailsRemoteDataSource.getActorDetails(actorId).toDomain(
                youtubeLink = socialMedia.youtubeId ?: "",
                facebookLink = socialMedia.facebookId ?: "",
                instagramLink = socialMedia.instagramId ?: ""
            )
        }

    override suspend fun getGallery(actorId: Int): Flow<List<String>> =
        tryToExecute(exception = CineVerseException.GalleryNotFoundException) {
            flow {
                emit(actorDetailsRemoteDataSource.getGallery(actorId).map { it.toDomain() })
            }.flowOn(ioDispatcher)
        }

    override suspend fun getBestOfMovies(actorId: Int): Flow<List<Movie>> =
        tryToExecute(exception = CineVerseException.BestOfMoviesNotFoundException) {
            flow {
                val bestMovies = actorDetailsRemoteDataSource.getBestOfMovies(actorId)
                val bestAsCast = bestMovies.cast.map { it.toDomain() }.sortedBy { it.rating }
                val bestAsCrew = bestMovies.crew.map { it.toDomain() }.sortedBy { it.rating }
                emit((bestAsCast + bestAsCrew).sortedBy { it.rating })
            }.flowOn(ioDispatcher)
        }
}