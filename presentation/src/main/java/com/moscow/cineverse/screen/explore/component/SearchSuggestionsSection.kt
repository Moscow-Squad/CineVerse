package com.moscow.cineverse.screen.explore.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.moscow.cineverse.screen.explore.ExploreInteractionListener
import com.moscow.cineverse.screen.explore.ExploreScreenState

@Composable
fun SearchSuggestionsSection(
    uiState: ExploreScreenState,
    interactionListener: ExploreInteractionListener,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = uiState.showSuggestions,
        enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
    ) {
        SearchSuggestion(
            modifier = modifier,
            suggestionList = uiState.displayedSuggestions,
            isHistory = uiState.showHistory,
            onClickSuggestion = interactionListener::onClickSuggestion,
            onClearAllClicked = interactionListener::clearAllLocalSuggestions
        )
    }
}