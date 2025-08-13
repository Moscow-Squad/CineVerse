package com.moscow.cineverse.screen.cast_details

import com.moscow.domain.model.ActorDetails
import com.moscow.domain.model.Movie

data class CastDetailsUiState(
    val isLoading: Boolean = false,
    val actorDetails: ActorDetails? = null,
    val movies: List<Movie> = emptyList(),
    val images: List<String> = emptyList(),
    val socialMediaLinks: SocialMediaLinks = SocialMediaLinks(),
    val shouldShowError: Boolean = false,
    val errorMessage: Int = 0,
    val isContentEmpty: Boolean = false,
    val isLoadingMovies: Boolean = false,
    val isLoadingImages: Boolean = false,
    val enableBlur: String = "high"
) {
    data class SocialMediaLinks(
        val youtube: String = "",
        val facebook: String = "",
        val instagram: String = "",
        val twitter: String = "",
        val tiktok: String = ""
    )
}