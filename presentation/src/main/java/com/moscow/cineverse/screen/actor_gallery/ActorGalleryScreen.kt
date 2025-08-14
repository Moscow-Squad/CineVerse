package com.moscow.cineverse.screen.actor_gallery

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moscow.cineverse.component.ErrorContent
import com.moscow.cineverse.designSystem.component.app_bar.MovieAppBar
import com.moscow.cineverse.designSystem.component.indicator.MovieCircularProgressBar
import com.moscow.cineverse.designSystem.component.wrapper.MovieScaffold
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.screen.actor_gallery.component.ActorGallery

@Composable
fun ActorGalleryScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    viewModel: ActorGalleryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            ActorGalleryEffectHandler.handleEffect(
                effect,
                navigateBack,
            )
        }
    }
    ActorGalleryContent(
        modifier = modifier,
        uiState = uiState,
        interactionListener = viewModel,
        onNavigateBack = navigateBack,
        title = uiState.actorName
    )
}

@Composable
fun ActorGalleryContent(
    uiState: ActorGalleryState,
    interactionListener: ActorGalleryInteractionListener,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    title: String
) {
    MovieScaffold {
        Box(modifier = modifier.fillMaxSize()) {
            when {
                uiState.isLoading -> {
                    MovieCircularProgressBar(
                        modifier = Modifier.align(Alignment.Center),
                        gradientColors = listOf(
                            Theme.colors.brand.primary,
                            Theme.colors.brand.tertiary
                        )
                    )
                }

                uiState.error != null -> {
                    ErrorContent(
                        errorMessage = uiState.error,
                        onRetry = interactionListener::onRefresh,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                else -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        MovieAppBar(
                            title = title,
                            backButtonClick = onNavigateBack,
                        )
                        ActorGallery(
                            images = uiState.photos,
                            enableBlur = uiState.enableBlur,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                        )
                    }
                }
            }
        }
    }
}
