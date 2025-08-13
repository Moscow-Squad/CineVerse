package com.moscow.cineverse.screen.actor_gallery

data class ActorGalleryState(
    val actorId: Int = 0,
    val actorName: String = "",
    val isLoading: Boolean = false,
    val error: Int? = null,
    val photos: List<String> = emptyList(),
    val enableBlur: String = "high"
)
