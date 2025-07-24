package com.data_source.remote

import com.remote.dto.actor.ActorBestOfMoviesDto
import com.remote.dto.actor.ActorImagesDto
import com.remote.dto.actor.ActorSocialMediaDto
import com.remote.dto.details.ActorDetailsDto

interface ActorRemoteDataSource {
    suspend fun getActorDetails(id: Int): ActorDetailsDto
    suspend fun getActorGallery(id: Int): ActorImagesDto
    suspend fun getActorBestMovies(id: Int): ActorBestOfMoviesDto
    suspend fun getActorSocialMedia(id: Int): ActorSocialMediaDto
}