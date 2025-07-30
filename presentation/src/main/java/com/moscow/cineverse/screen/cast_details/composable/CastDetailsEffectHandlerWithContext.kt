package com.moscow.cineverse.screen.cast_details.composable

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import com.moscow.cineverse.screen.cast_details.CastDetailsEffect

object CastDetailsEffectHandlerWithContext {

    fun handleEffectWithContext(
        effect: CastDetailsEffect,
        context: Context,
        navigateBack: () -> Unit,
        navigateToMovieDetails: (Int) -> Unit,
        navigateToCastBestOfMovie: (Int, String) -> Unit,
        navigateToCastGallery: (Int, String) -> Unit,

        ) {
        when (effect) {
            is CastDetailsEffect.NavigateBack -> {
                navigateBack()
            }

            is CastDetailsEffect.ShowError -> {
                // TODO: Show error (Snackbar, Toast, etc.)
            }

            is CastDetailsEffect.OpenSocialMedia -> {
                val intent = Intent(Intent.ACTION_VIEW, effect.url.toUri())
                context.startActivity(intent)
            }

            is CastDetailsEffect.NavigateToMovie -> {
                navigateToMovieDetails(effect.movieId)
            }

            is CastDetailsEffect.NavigateToFullMovieList -> {
                navigateToCastBestOfMovie(effect.actorId, effect.actorName)
            }

            is CastDetailsEffect.NavigateToFullGallery -> {
                navigateToCastGallery(effect.actorId, effect.actorName)
            }
        }
    }
}