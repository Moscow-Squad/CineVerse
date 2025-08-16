package com.moscow.cineverse.common_ui_state

import kotlinx.datetime.LocalDate

data class MediaItemUiState(
    val id: Int = 0,
    val title: String = "",
    val posterPath: String = "",
    val rating: Float = 0.0f,
    val genres: List<String> = emptyList(),
    val releaseDate: LocalDate? = null,
    val backdropPath: String = "",
    val mediaType: MediaType = MediaType.Movie,
) {
    enum class MediaType {
        Movie, Series;

        companion object {
            fun toMediaType(name: String) =
                MediaType.entries.find { it.name.equals(name, ignoreCase = true) } ?: Movie
        }
    }
}