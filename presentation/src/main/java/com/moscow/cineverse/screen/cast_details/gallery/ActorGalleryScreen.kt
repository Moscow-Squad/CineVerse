package com.moscow.cineverse.screen.cast_details.gallery

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moscow.cineverse.component.ErrorContent
import com.moscow.cineverse.designSystem.component.CineVersePreviews
import com.moscow.cineverse.designSystem.component.MovieAppBar
import com.moscow.cineverse.designSystem.component.MovieCircularProgressBar
import com.moscow.cineverse.designSystem.component.MovieScaffold
import com.moscow.cineverse.designSystem.component.button.MovieButton
import com.moscow.cineverse.designSystem.component.wrapper.MovieText
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cinverse.presentation.R

@Composable
fun ActorGalleryScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
) {
    val viewModel: ActorGalleryViewModel = hiltViewModel()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
    uiState: ShowAllActorMoviesState,
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
