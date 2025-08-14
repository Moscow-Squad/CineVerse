package com.moscow.cineverse.details.movie_details.component

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.net.toUri
import com.moscow.cineverse.details.movie_details.MovieDetailsScreenEffect

object MovieDetailsEffectHandler {

    fun handleEffect(
        effect: MovieDetailsScreenEffect,
        navigateBack: () -> Unit,
        navigateToRecommendations: (Int, String) -> Unit,
        navigateToReviews: (Int) -> Unit,
        navigateToCastDetails: (Int) -> Unit,
        navigateToCollectionsBottomSheet: (Int) -> Unit,
        navigateToMovieDetails: (Int) -> Unit,
        navigateToLogin: () -> Unit,
        context: Context
    ) {
        when (effect) {
            is MovieDetailsScreenEffect.NavigateBack -> {
                navigateBack()
            }

            is MovieDetailsScreenEffect.ShowError -> {
                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }

            is MovieDetailsScreenEffect.NavigateToFullMovieList -> {
                navigateToRecommendations(
                    effect.movieID,
                    effect.movieTitle
                )
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

            is MovieDetailsScreenEffect.NavigateMovieDetails -> {
                navigateToMovieDetails(effect.movieId)
            }

            is MovieDetailsScreenEffect.OpenTrailer -> {
                val intent = Intent(Intent.ACTION_VIEW, effect.url.toUri())
                context.startActivity(intent)
            }

            is MovieDetailsScreenEffect.NavigateToLogin -> {
                navigateToLogin()
            }
        }
    }
}