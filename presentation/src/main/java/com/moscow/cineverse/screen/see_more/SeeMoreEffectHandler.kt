package com.moscow.cineverse.screen.see_more

object SeeMoreEffectHandler {
    fun handleEffect(
        effect: SeeMoreEvent,
        navigateToMovieDetails: (Int) -> Unit,
        navigateToSeriesDetails: (Int) -> Unit,
        navigateToCastDetails: (Int) -> Unit,
        navigateBack: () -> Unit
    ) {
        when (effect) {
            is SeeMoreEvent.MovieClicked -> {
                navigateToMovieDetails(effect.movieId)
            }

            is SeeMoreEvent.SeriesClicked -> {
                navigateToSeriesDetails(effect.seriesId)
            }

            is SeeMoreEvent.ActorClicked -> {
                navigateToCastDetails(effect.actorId)
            }

            is SeeMoreEvent.NavigateBack -> {
                navigateBack()
            }
        }
    }
}