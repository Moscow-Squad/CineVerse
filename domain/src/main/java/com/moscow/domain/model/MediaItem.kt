package com.moscow.domain.model

import kotlinx.datetime.LocalDate

data class MediaItem(
    val id: Int,
    val title: String,
    val posterPath: String,
    val rating: Float,
    val genres: List<String>,
    val releaseDate: LocalDate?,
    val backdropPath: String,
    val mediaType: MediaType,
) {
    enum class MediaType {
        Movie, Series;

        companion object {
            fun toMediaType(name: String) =
                MediaType.entries.find { it.name.equals(name, ignoreCase = true) } ?: Movie
        }
    }

}
