package com.moscow.cineverse.screen.details.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.component.MediaPosterCard
import com.moscow.cineverse.component.MovieListSection
import com.moscow.cineverse.utlis.ViewMode
import com.moscow.cinverse.presentation.R


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun RecommendationsSection(
    mediaItems: List<MediaItemUiState>,
    modifier: Modifier = Modifier,
    onShowMoreClicked: () -> Unit,
    onMediaItemClick: (id: Int) -> Unit,
    enableBlur: String
) {
    AnimatedVisibility(mediaItems.isNotEmpty()) {
        MovieListSection(
            title = stringResource(R.string.you_might_also_like),
            mediaItems = mediaItems,
            onClickShowMore = onShowMoreClicked,
            onClickPoster = { movie -> },
            modifier = modifier.padding(top = 16.dp),
            movieCardContent = { mediaItem, cardModifier, onClick ->
                MediaPosterCard(
                    mediaItem = mediaItem,
                    viewMode = ViewMode.GRID,
                    showRating = true,
                    onMediaItemClick = { onMediaItemClick(mediaItem.id) },
                    showTitle = true,
                    modifier = cardModifier,
                    getTitleOverride = { it.title.take(15) + if (it.title.length > 15) "…" else "" },
                    enableBlur = enableBlur,
                )
            }
        )
    }

}