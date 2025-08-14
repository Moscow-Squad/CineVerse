package com.moscow.cineverse.details.series_details

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri

object SeriesDetailsEffectHandler {
    fun handleSeriesDetailsEvents(
        event: SeriesDetailsScreenEffects,
        context: Context,
        navigateToCollectionBottomSheet: (Int) -> Unit,
        navigateToSeriesRecommendation: (Int, String) -> Unit,
        navigateToReviews: (Int) -> Unit,
        navigateToSeriesSeasons: (Int) -> Unit,
        navigateToCastDetails: (Int) -> Unit,
        navigateToSeriesDetails: (Int) -> Unit,
        navigateToLogin: () -> Unit
    ) {
        when (event) {
            is SeriesDetailsScreenEffects.AddToCollection -> navigateToCollectionBottomSheet(event.seriesId)

            is SeriesDetailsScreenEffects.NavigateToRecommendationSeries -> navigateToSeriesRecommendation(
                event.seriesId,
                event.seriesName
            )

            is SeriesDetailsScreenEffects.NavigateToReviewsScreen -> navigateToReviews(event.seriesId)

            is SeriesDetailsScreenEffects.NavigateToSeriesSeasonsScreen -> navigateToSeriesSeasons(
                event.seriesId
            )

            is SeriesDetailsScreenEffects.NavigateToActorDetailsScreen -> navigateToCastDetails(
                event.ActorId
            )

            is SeriesDetailsScreenEffects.NavigateToSeriesDetailsScreen -> navigateToSeriesDetails(
                event.seriesId
            )

            is SeriesDetailsScreenEffects.OpenTrailer -> {
                val intent = Intent(Intent.ACTION_VIEW, event.url.toUri())
                context.startActivity(intent)
            }

            is SeriesDetailsScreenEffects.NavigateToLogin -> navigateToLogin()
        }
    }

}