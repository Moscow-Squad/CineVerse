package com.moscow.cineverse.screen.castDetails

import com.moscow.domain.model.Movie

interface CastDetailsInteractionListener {
    fun onBackPressed()
    fun onSocialMediaClick(platform: String, url: String)
    fun onRefresh()
    fun onMovieClick(movie: Movie)
    fun onShowMoreMovies()
    fun onShowMoreGallery()
}