package com.moscow.cineverse.designSystem.component

data class MovieUI(
    val id: Int = 0,
    val title: String = "",
    val posterUrl: String = "",
    val rating: Float = 0f,
    val genres: List<String> = emptyList(),
    val duration: String = "",
    val releaseDate: String = ""
)
