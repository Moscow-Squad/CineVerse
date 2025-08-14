package com.moscow.cineverse.screen.explore

object ExploreScreenEffectHandler {
    fun handleEffect(
        effect: ExploreScreenEffects,
        navigateToCastDetails: (Int) -> Unit,
        navigateToMovieDetails: (Int) -> Unit,
        navigateToSeriesDetails: (Int) -> Unit,
    ) {
        when (effect) {
            is ExploreScreenEffects.ActorClicked -> {
                navigateToCastDetails(effect.actorId)
            }

            is ExploreScreenEffects.GenreSelected -> {}
            ExploreScreenEffects.LoadData -> {}
            is ExploreScreenEffects.MovieClicked -> {
                navigateToMovieDetails(effect.movieId)
            }

            ExploreScreenEffects.RefreshRequested -> {}
            is ExploreScreenEffects.TabSelected -> {}
            is ExploreScreenEffects.ViewModeChanged -> {}
            is ExploreScreenEffects.SeriesClicked -> {
                navigateToSeriesDetails(effect.seriesId)
            }
        }
    }
}