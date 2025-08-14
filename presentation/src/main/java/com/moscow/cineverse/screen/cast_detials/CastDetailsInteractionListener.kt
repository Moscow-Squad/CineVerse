package com.moscow.cineverse.screen.cast_detials

import com.moscow.cineverse.common_ui_state.MediaItemUiState

interface CastDetailsInteractionListener {
    fun onBackPressed()
    fun onSocialMediaClick(platform: String, url: String)
    fun onRefresh()
    fun onMovieClick(movie: MediaItemUiState)
    fun onShowMoreMovies()
    fun onShowMoreGallery()
}