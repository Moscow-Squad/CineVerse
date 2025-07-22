package com.repository.actordetails

import com.android.domain.model.ActorDetails
import com.android.domain.model.Movie
import com.android.domain.repository.ActorDetailsRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.mapper.toDomain
import com.remote.interceptors.CineverseInterceptor
import com.remote.services.ActorDetailsService
import com.remote.source.ActorDetailsRemoteDataSource
import com.utils.BASE_URL
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

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


