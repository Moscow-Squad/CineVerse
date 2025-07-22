package com.moscow.cineverse.screen.castDetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.moscow.cineverse.designSystem.component.MovieAppBar
import com.moscow.cineverse.designSystem.component.MovieScaffold
import com.moscow.cineverse.navigation.LocalNavController
import com.moscow.cineverse.screen.castDetails.components.CastDetailsContent
import com.moscow.cineverse.screen.castDetails.components.CastDetailsEffectHandlerWithContext
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