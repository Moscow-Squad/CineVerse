package com.moscow.cineverse.screen.movie_details.component

import com.moscow.cineverse.screen.movie_details.MovieDetailsScreenEffect

object MovieDetailsEffectHandler {

    fun handleEffect(
        effect: MovieDetailsScreenEffect,
        navigateBack: () -> Unit,
        navigateToRecommendations: (Int, String) -> Unit,
        navigateToReviews: (Int) -> Unit,
        navigateToCastDetails: (Int) -> Unit,
        navigateToCollectionsBottomSheet: (Int) -> Unit,


    ) {
        when (effect) {
            is MovieDetailsScreenEffect.NavigateBack -> {
                navigateBack()
            }

            is MovieDetailsScreenEffect.ShowError -> {
                // TODO: Show error (Snackbar, Toast, etc.)
            }

            is MovieDetailsScreenEffect.NavigateToFullMovieList -> {
                navigateToRecommendations(
                    effect.movieID,
                    effect.movieTitle
                )

            }

            is MovieDetailsScreenEffect.NavigateToFullActors -> {
                // TODO: Implement navigation to full actors list
            }

            is MovieDetailsScreenEffect.NavigateToFullReviews -> {
                navigateToReviews(effect.movieID)

            }

            is MovieDetailsScreenEffect.NavigateCastDetails -> {
                navigateToCastDetails(effect.castId)
            }

            is MovieDetailsScreenEffect.AddToCollection -> {
                navigateToCollectionsBottomSheet(effect.movieId)

            }

            MovieDetailsScreenEffect.NavigateToFullCast -> {
                // TODO: Implement navigation to full cast
            }
        }
    }
}