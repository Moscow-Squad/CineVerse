package com.moscow.domain.model

data class MediaItem(
    val id: Int,
    val adult: Boolean,
    val backdropPath: String,
    val firstAirDate: String,
    val genreIds: List<Int>,
    val mediaType: MediaType,
    val name: String,
    val originalName: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val rate: Float,
){
    enum class MediaType {
        Movie,
        Tv,
        Person,
        Unknown;

        override fun toString(): String {
            return name.lowercase()
        }
        companion object {
            fun toMediaType(value: String): MediaType {
                return entries.find { it.name.equals(value, ignoreCase = true) } ?: Unknown
            }
        }
    }
}