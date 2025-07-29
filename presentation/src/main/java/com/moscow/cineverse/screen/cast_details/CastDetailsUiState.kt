package com.moscow.cineverse.screen.cast_details

import com.moscow.cineverse.designSystem.component.cast_details.SocialMediaLinks
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
    val isLoadingImages: Boolean = false
)