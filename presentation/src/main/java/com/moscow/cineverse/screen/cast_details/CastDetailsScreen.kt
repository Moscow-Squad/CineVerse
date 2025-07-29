package com.moscow.cineverse.screen.cast_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.moscow.cineverse.designSystem.component.MovieAppBar
import com.moscow.cineverse.designSystem.component.MovieScaffold
import com.moscow.cineverse.navigation.LocalNavController
import com.moscow.cineverse.screen.cast_details.components.ActorBiographySection
import com.moscow.cineverse.screen.cast_details.components.ActorGallerySection
import com.moscow.cineverse.screen.cast_details.components.ActorMainDetailsSection
import com.moscow.cineverse.screen.cast_details.components.ActorMoviesSection
import com.moscow.cineverse.screen.cast_details.components.CastDetailsEffectHandlerWithContext
import com.moscow.cineverse.screen.cast_details.components.EmptyContent
import com.moscow.cineverse.screen.cast_details.components.ErrorContent
import com.moscow.cineverse.screen.cast_details.components.LoadingContent

@Composable
fun CastDetailsScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = LocalNavController.current,
    viewModel: CastDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(viewModel) {
        viewModel.uiEffect.collect { effect ->
            CastDetailsEffectHandlerWithContext.handleEffectWithContext(
                effect = effect,
                navController = navController,
                context = context
            )
        }
    }

    MovieScaffold {
        Column(modifier = modifier.fillMaxSize()) {
            MovieAppBar(
                title = uiState.actorDetails?.name ?: "",
                backButtonClick = { navController.popBackStack() },
                showBackButton = true,
                showAddButton = false,
                showLogo = false,
                showDivider = true
            )

            CastDetailsContent(
                uiState = uiState,
                interactionListener = viewModel,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun CastDetailsContent(
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