package com.moscow.cineverse.screen.explore

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moscow.cineverse.designSystem.component.search.SearchBar
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.screen.explore.component.SearchSuggestion
import org.koin.androidx.compose.koinViewModel

@Composable
fun ExploreScreen(viewModel: ExploreViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ExploreComponent(
        uiState = uiState,
        exploreListener = viewModel
    )
}

@Composable
fun ExploreComponent(
    uiState: ExploreScreenState,
    exploreListener: ExploreInteractionListener
) {
    val searchText = uiState.searchKeyWord
    val suggestions = uiState.history
    val showHistory = uiState.showHistory
    val showSuggestions = uiState.showSuggestions
    val remoteSuggestions = uiState.suggestions

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Theme.colors.background.screen)
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        SearchBar(
            modifier = Modifier.fillMaxWidth().padding(top = 56.dp),
            value = searchText,
            onValueChange = { exploreListener.onSearchValueChange(it) },
            onCancelButtonClicked = { exploreListener.onCancelButtonClicked() },
            onFirstFocus = { exploreListener.onSearchBarClickedOn() },
            trailingIcon = {
                VoiceRecognitionIcon(
                    modifier = Modifier.size(20.dp),
                    onResult = { exploreListener.onSearchValueChange(it.toString()) },
                    onError = {}
                )
            }
        )

        AnimatedVisibility(
            visible = showSuggestions,
            enter = slideInVertically(
                initialOffsetY = {it},
                animationSpec = tween(500)
            ) + fadeIn(animationSpec = tween(500)),
            exit = slideOutVertically(
                targetOffsetY = {it},
                animationSpec = tween(500)
            ) + fadeOut(animationSpec = tween(500))
        ) {
            SearchSuggestion(
                modifier = Modifier.padding(top = 24.dp),
                suggestionList = suggestions + remoteSuggestions.map { SuggestItemUiState(it.name, isHistory = false) },
                isHistory = showHistory,
                onClearAllClicked = {}
            )
        }
    }
}