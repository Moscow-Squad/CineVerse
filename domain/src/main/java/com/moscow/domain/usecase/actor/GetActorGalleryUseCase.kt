package com.moscow.domain.usecase.actor

import com.moscow.domain.repository.ActorRepository

class GetActorGalleryUseCase(
    private val actorRepository: ActorRepository
) {
    suspend operator fun invoke(actorId: Int): List<String> =
        actorRepository.getActorGallery(actorId)
}