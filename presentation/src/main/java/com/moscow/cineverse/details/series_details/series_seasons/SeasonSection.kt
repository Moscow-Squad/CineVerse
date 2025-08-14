package com.moscow.cineverse.details.series_details.series_seasons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.details.series_details.SeasonUiState

@Composable
fun SeasonSection(
    seasons: List<SeasonUiState>,
    enableBlur: String
) {
    seasons.forEach { season ->
        SeasonCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            seasonNumber = season.title,
            episodeCount = season.episodeCount,
            airDate = season.airDate,
            posterUrl = season.posterPath,
            caption = season.overview,
            rate = season.rate,
            enableBlur = enableBlur,
        )
    }

}