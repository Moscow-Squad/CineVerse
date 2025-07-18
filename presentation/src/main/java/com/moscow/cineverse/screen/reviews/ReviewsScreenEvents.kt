package com.moscow.cineverse.screen.reviews

sealed class ReviewsScreenEvents {
    data object NavigateBack : ReviewsScreenEvents()
    data class ShowError(val message: String) : ReviewsScreenEvents()

}

