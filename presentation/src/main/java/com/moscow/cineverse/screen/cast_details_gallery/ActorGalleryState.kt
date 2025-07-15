package com.moscow.cineverse.screen.cast_details_gallery

data class ShowAllActorMoviesState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val photos : List<String> = emptyList(),
)
