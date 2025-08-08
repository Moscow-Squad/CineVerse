package com.moscow.cineverse.screen.cast_details.gallery

data class ShowAllActorMoviesState(
    val actorId: Int = 0,
    val actorName: String = "",
    val isLoading: Boolean = false,
    val error: Int? = null,
    val photos : List<String> = emptyList(),
    val enableBlur: String = "high"
)
