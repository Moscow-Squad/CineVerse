package com.moscow.cineverse.screen.castDetails.components

import android.content.Intent
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import com.moscow.cineverse.navigation.routes.CastBestOfMovieRoute
import com.moscow.cineverse.navigation.routes.CastGalleryRoute
import com.moscow.cineverse.navigation.routes.MovieDetailsRoute
import com.moscow.cineverse.screen.castDetails.CastDetailsEffect

object CastDetailsEffectHandlerWithContext {

    fun handleEffectWithContext(
        effect: CastDetailsEffect,
        navController: NavHostController,
        context: android.content.Context
    ) {
        when (effect) {
            is CastDetailsEffect.NavigateBack -> {
                navController.popBackStack()
            }

            is CastDetailsEffect.ShowError -> {
                // TODO: Show error (Snackbar, Toast, etc.)
            }

            is CastDetailsEffect.OpenSocialMedia -> {
                val intent = Intent(Intent.ACTION_VIEW, effect.url.toUri())
                context.startActivity(intent)
            }

            is CastDetailsEffect.NavigateToMovie -> {
                navController.navigate(MovieDetailsRoute(effect.movieId))
            }

            is CastDetailsEffect.NavigateToFullMovieList -> {
                navController.navigate(
                    CastBestOfMovieRoute(effect.actorId, effect.actorName)
                )
            }

            is CastDetailsEffect.NavigateToFullGallery -> {
                navController.navigate(
                    CastGalleryRoute(effect.actorId, effect.actorName)
                )
            }
        }
    }
}