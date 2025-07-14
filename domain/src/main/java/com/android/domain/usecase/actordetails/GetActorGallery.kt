package com.android.domain.usecase.actordetails

import com.android.domain.exception.CineVerseException
import com.android.domain.repository.ActorDetailsRepository
import kotlinx.coroutines.flow.Flow

class GetActorGallery(private val actorDetailsRepository: ActorDetailsRepository) {
    suspend fun getActorGallery(actorId: Int, page: Int): Flow<List<String>> {
        return actorDetailsRepository.getGallery(actorId)
    }
}