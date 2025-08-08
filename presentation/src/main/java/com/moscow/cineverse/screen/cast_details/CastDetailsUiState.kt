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
    val errorMessage: String = "",
    val isContentEmpty: Boolean = false,
    val isLoadingMovies: Boolean = false,
    val isLoadingImages: Boolean = false,
    val enableBlur: String = "high"
) {
    data class SocialMediaLinks(
        val youtube: String? = null,
        val facebook: String? = null,
        val instagram: String? = null
    )
}