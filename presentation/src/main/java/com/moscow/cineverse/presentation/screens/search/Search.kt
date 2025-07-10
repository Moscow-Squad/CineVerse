package com.moscow.cineverse.presentation.screens.search

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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.search.SearchBar
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.presentation.screens.search.component.SearchSuggestion
import org.koin.androidx.compose.koinViewModel

@Composable
fun Search(viewModel: SearchViewModel = koinViewModel()) {

    val searchText = viewModel.uiState.collectAsState().value.searchText
    val suggestions = viewModel.uiState.collectAsState().value.history
    val showHistory = viewModel.uiState.collectAsState().value.showHistory
    val showSuggestions = viewModel.uiState.collectAsState().value.showSuggestions

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
            onValueChange = { viewModel.onSearchValueChange(it) },
            onCancelButtonClicked = { viewModel.onCancelButtonClicked() },
            onFirstFocus = { viewModel.onSearchBarClickedOn() },
            trailingIcon = {
                VoiceRecognitionIcon(
                    modifier = Modifier.size(20.dp),
                    onResult = {viewModel.onSearchValueChange(it.toString())},
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
                suggestionList = suggestions,
                isHistory = showHistory,
                onClearAllClicked = {}
            )
        }
    }
}