package com.moscow.cineverse.screen.castDetails

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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.moscow.cineverse.designSystem.component.MovieAppBar
import com.moscow.cineverse.designSystem.component.MovieScaffold
import com.moscow.cineverse.navigation.LocalNavController
import com.moscow.cineverse.screen.castDetails.components.ActorBiographySection
import com.moscow.cineverse.screen.castDetails.components.ActorGallerySection
import com.moscow.cineverse.screen.castDetails.components.ActorMainDetailsSection
import com.moscow.cineverse.screen.castDetails.components.ActorMoviesSection
import com.moscow.cineverse.screen.castDetails.components.CastDetailsEffectHandlerWithContext
import com.moscow.cineverse.screen.castDetails.components.EmptyContent
import com.moscow.cineverse.screen.castDetails.components.ErrorContent
import com.moscow.cineverse.screen.castDetails.components.LoadingContent
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun CastDetailsScreen(
    actorId: Int,
    modifier: Modifier = Modifier,
    navController: NavHostController = LocalNavController.current,
    viewModel: CastDetailsViewModel = koinViewModel(parameters = { parametersOf(actorId) })
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