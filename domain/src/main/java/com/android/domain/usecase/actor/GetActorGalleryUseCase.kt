package com.android.domain.usecase.actor

import com.android.domain.repository.ActorRepository

class GetActorGalleryUseCase(private val actorRepository: ActorRepository) {
    suspend operator fun invoke(actorId: Int): List<String> =
        actorRepository.getActorGallery(actorId)
}