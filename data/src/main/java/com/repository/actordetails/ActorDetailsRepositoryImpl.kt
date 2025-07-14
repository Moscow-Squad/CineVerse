package com.repository.actordetails

import com.android.domain.exception.CineVerseException
import com.android.domain.exception.CineVerseException.NoMoviesFoundException
import com.android.domain.model.ActorDetails
import com.android.domain.model.Movie
import com.android.domain.repository.ActorDetailsRepository
import com.mapper.toDomain
import com.remote.actordetails.ActorDetailsRemoteDataSource
import com.utils.BaseRepository

class ActorDetailsRepositoryImpl(
    private val actorDetailsRemoteDataSource: ActorDetailsRemoteDataSource,
) : ActorDetailsRepository, BaseRepository() {
    override suspend fun getActorDetails(actorId: Int): ActorDetails
    = tryToExecute(exception = CineVerseException.ActorDetailsNotFoundException){
        actorDetailsRemoteDataSource.getActorDetails(actorId).toDomain()
    }

    override suspend fun getGallery(actorId: Int): List<String>
    = tryToExecute(exception = CineVerseException.GalleryNotFoundException){
        actorDetailsRemoteDataSource.getGallery(actorId).map { it.toDomain() }
    }

    override suspend fun getBestOfMovies(actorId: Int): List<Movie>
    = tryToExecute(exception = CineVerseException.BestOfMoviesNotFoundException){
        val bestMovies = actorDetailsRemoteDataSource.getBestOfMovies(actorId)
        val bestAsCast = bestMovies.cast.map { it.toDomain() }.sortedBy{it.rating}
        val bestAsCrew = bestMovies.crew.map { it.toDomain() }.sortedBy { it.rating }
        (bestAsCast + bestAsCrew).sortedBy { it.rating }
    }
}