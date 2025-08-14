package com.moscow.cineverse.screen.cast_detials

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri

object CastDetailsEffectHandler {

    fun handleEffect(
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