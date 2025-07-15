package com.moscow.cineverse.screen.castDetails.gallery

data class ShowAllActorMoviesState(
    val actorId: Int = 0,
    val actorName: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val photos : List<String> = emptyList(),
)
