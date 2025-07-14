package com.android.domain.usecase.actordetails

import com.android.domain.exception.CineVerseException
import com.android.domain.repository.ActorDetailsRepository

class GetActorGallery(private val actorDetailsRepository: ActorDetailsRepository) {
    suspend operator fun invoke(actorId: Int, page: Int): List<String> {
        val gallery = actorDetailsRepository.getGallery(actorId)
        return if (gallery.isEmpty()) throw CineVerseException.NoGalleryFoundException else gallery
    }
}