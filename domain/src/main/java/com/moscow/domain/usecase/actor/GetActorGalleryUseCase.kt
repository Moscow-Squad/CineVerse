package com.moscow.domain.usecase.actor

import com.moscow.domain.repository.ActorRepository
import javax.inject.Inject

class GetActorGalleryUseCase @Inject constructor(
    private val actorRepository: ActorRepository
) {
    suspend operator fun invoke(actorId: Int): List<String> =
        actorRepository.getActorGalleryUrl(actorId)
}