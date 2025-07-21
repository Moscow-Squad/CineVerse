package com.repository.actordetails

import com.android.domain.model.ActorDetails
import com.android.domain.model.Movie
import com.android.domain.repository.ActorDetailsRepository
import com.mapper.toDomain
import com.remote.source.ActorDetailsRemoteDataSource
import kotlinx.coroutines.runBlocking
import org.koin.compose.getKoin
import org.koin.core.Koin

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


    override suspend fun getGallery(actorId: Int) =
        actorDetailsRemoteDataSource.getGallery(actorId).profiles.map { it.toDomain() }


    override suspend fun getBestOfMovies(actorId: Int): List<Movie> {

        val bestMovies = actorDetailsRemoteDataSource.getBestOfMovies(actorId)
        val bestAsCast = bestMovies.cast.mapNotNull { runCatching { it.toDomain() }.getOrNull() }
        val bestAsCrew = bestMovies.crew.mapNotNull { runCatching { it.toDomain() }.getOrNull() }
        return (bestAsCast + bestAsCrew).sortedByDescending { it.rating }
    }

}


fun main() = runBlocking {


//   /* val actorDetailsRemoteDataSource: ActorDetailsRemoteDataSource = getKoin().get() //Koin().get()
//    // Create repository instance
//    val repository = ActorDetailsRepositoryImpl(actorDetailsRemoteDataSource)
//
//    // Replace with a real actor ID that exists in your API
//    val actorId = 287 // e.g., Brad Pitt on TMDB
//
//    try {
//        val actorDetails = repository.getActorDetails(actorId)
//        println("Actor Details:\n$actorDetails\n")
//
//        val gallery = repository.getGallery(actorId)
//        println("Gallery:\n$gallery\n")
//
//        val bestMovies = repository.getBestOfMovies(actorId)
//        println("Best Movies:\n$bestMovies\n")
//
//    } catch (e: Exception) {
//        println("Error: ${e.message}")
//    }*/

    println("Actor Repo")
}