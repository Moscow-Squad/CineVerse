package com.moscow.cineverse.screen.actor_gallery

object ActorGalleryEffectHandler {
    fun handleEffect(
        effect: ActorGalleryEffect,
        navigateBack: () -> Unit,
        ) {
        when (effect) {
            ActorGalleryEffect.NavigateBack -> {
                navigateBack()
            }
        }
    }
}