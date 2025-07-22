package com.moscow.cineverse.screen.castDetails.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.screen.castDetails.CastDetailsInteractionListener
import com.moscow.cineverse.screen.castDetails.CastDetailsUiState

@Composable
fun CastDetailsContent(
    uiState: CastDetailsUiState,
    interactionListener: CastDetailsInteractionListener,
    modifier: Modifier = Modifier
) {
    when {
        uiState.isLoading -> {
            LoadingContent(modifier = modifier)
        }

        uiState.shouldShowError -> {
            ErrorContent(
                errorMessage = uiState.errorMessage,
                onRetry = { interactionListener.onRefresh() },
                modifier = modifier
            )
        }

        uiState.isContentEmpty -> {
            EmptyContent(modifier = modifier)
        }

        uiState.actorDetails != null -> {
            ActorDetailsContent(
                uiState = uiState,
                interactionListener = interactionListener,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun ActorDetailsContent(
    uiState: CastDetailsUiState,
    interactionListener: CastDetailsInteractionListener,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        item {
            ActorMainDetailsSection(
                uiState = uiState,
                interactionListener = interactionListener,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            ActorMoviesSection(
                uiState = uiState,
                interactionListener = interactionListener,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        item {
            ActorGallerySection(
                uiState = uiState,
                interactionListener = interactionListener,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }

        item {
            ActorBiographySection(
                uiState = uiState,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}