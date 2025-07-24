package com.moscow.cineverse.screen.movie_details.component

import androidx.navigation.NavHostController
import com.moscow.cineverse.navigation.routes.CastDetailsRoute
import com.moscow.cineverse.navigation.routes.CollectionsBottomSheetRoute
import com.moscow.cineverse.navigation.routes.RecommendationsRoute
import com.moscow.cineverse.navigation.routes.ReviewsRoute
import com.moscow.cineverse.screen.movie_details.MovieDetailsScreenEffect

object MovieDetailsEffectHandler {

    fun handleEffect(
        effect: MovieDetailsScreenEffect,
        navController: NavHostController
    ) {
        when (effect) {
            is MovieDetailsScreenEffect.NavigateBack -> {
                navController.popBackStack()
            }

            is MovieDetailsScreenEffect.ShowError -> {
                // TODO: Show error (Snackbar, Toast, etc.)
            }

            is MovieDetailsScreenEffect.NavigateToFullMovieList -> {
                navController.navigate(
                    RecommendationsRoute(effect.movieID, effect.movieTitle)
                )
            }

            is MovieDetailsScreenEffect.NavigateToFullActors -> {
                // TODO: Implement navigation to full actors list
            }

            is MovieDetailsScreenEffect.NavigateToFullReviews -> {
                navController.navigate(ReviewsRoute(effect.movieID, true))
            }

            is MovieDetailsScreenEffect.NavigateCastDetails -> {
                navController.navigate(CastDetailsRoute(effect.castId))
            }

            is MovieDetailsScreenEffect.AddToCollection -> {
                navController.navigate(CollectionsBottomSheetRoute(effect.movieId))
            }

            MovieDetailsScreenEffect.NavigateToFullCast -> {
                // TODO: Implement navigation to full cast
            }
        }
    }
}