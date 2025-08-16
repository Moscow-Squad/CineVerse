package com.moscow.cineverse.screen.details.movie_details.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.common_ui_state.CastUiState
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.screen.details.common.CastCard
import com.moscow.cineverse.screen.details.common.StarCastSection
import com.moscow.cinverse.presentation.R

@Composable
fun MovieCastSection(
    castMembers: List<CastUiState>,
    onActorClicked: (id: Int) -> Unit,
    modifier: Modifier = Modifier,
    enableBlur: String,
) {
    AnimatedVisibility(castMembers.isNotEmpty()) {
        StarCastSection(
            title = stringResource(R.string.star_cast),
            modifier = modifier
                .background(Theme.colors.background.screen)
                .padding(top = 24.dp, start = 16.dp, end = 16.dp),
            cast = castMembers.take(10),
            castContent = { actor ->
                CastCard(
                    enableBlur = enableBlur,
                    modifier = Modifier.clickable { onActorClicked(actor.id) },
                    castMember = actor,
                )
            }
        )
    }
}

