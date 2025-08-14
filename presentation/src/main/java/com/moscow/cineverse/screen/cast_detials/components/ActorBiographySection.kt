package com.moscow.cineverse.screen.cast_detials.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.screen.cast_detials.CastDetailsUiState
import com.moscow.cineverse.screen.details.common.InfoSection
import com.moscow.cinverse.presentation.R

@Composable
fun ActorBiographySection(
    uiState: CastDetailsUiState, modifier: Modifier = Modifier
) {
    uiState.actor?.let { actorDetails ->
        if (actorDetails.biography.isNotEmpty()) {
            Box(
                modifier = modifier
            ) {
                InfoSection(
                    title = stringResource(R.string.biography),
                    description = actorDetails.biography,
                    showGenres = false,
                    maxDescriptionLines = Int.MAX_VALUE,
                    paddingTop = 8.dp,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    showRating = false
                )
            }
        }
    }
}