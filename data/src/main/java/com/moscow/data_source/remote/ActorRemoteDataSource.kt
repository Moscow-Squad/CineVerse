package com.moscow.data_source.remote

import com.moscow.remote.dto.actor.ActorBestOfMoviesDto
import com.moscow.remote.dto.actor.ActorImagesDto
import com.moscow.remote.dto.actor.ActorSocialMediaDto
import com.moscow.remote.dto.actor.ActorDetailsDto

interface ActorRemoteDataSource {
    suspend fun getActorDetails(id: Int): ActorDetailsDto
    suspend fun getActorGallery(id: Int): ActorImagesDto
    suspend fun getActorBestMovies(id: Int): ActorBestOfMoviesDto
    suspend fun getActorSocialMedia(id: Int): ActorSocialMediaDto
}