package com.android.domain.model

enum class MediaType {
    Movie,
    Tv,
    Person,
    Unknown;
    companion object {
        fun toMediaType(value: String): MediaType {
            return entries.find { it.name.equals(value, ignoreCase = true) } ?: Unknown
        }
    }
}