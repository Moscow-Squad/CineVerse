package com.moscow.cineverse.screen.cast_detials

import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.domain.model.Actor

data class CastDetailsUiState(
    val isLoading: Boolean = false,
    val actor: Actor? = null,
    val movies: List<MediaItemUiState> = emptyList(),
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
        val youtube: String? = null,
        val facebook: String? = null,
        val instagram: String? = null,
        val twitter: String? = null,
        val tiktok: String? = null
    )
}