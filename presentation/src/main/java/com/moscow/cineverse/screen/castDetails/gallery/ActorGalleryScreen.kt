package com.moscow.cineverse.screen.castDetails.gallery

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.moscow.cineverse.designSystem.component.CineVersePreviews
import com.moscow.cineverse.designSystem.component.MovieAppBar
import com.moscow.cineverse.designSystem.component.MovieButton
import com.moscow.cineverse.designSystem.component.MovieCircularProgressBar
import com.moscow.cineverse.designSystem.component.MovieScaffold
import com.moscow.cineverse.designSystem.component.MovieText
import com.moscow.cineverse.designSystem.component.cast_details.CastGallery
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.navigation.LocalNavController
import com.moscow.cinverse.presentation.R
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ActorGalleryScreen(
    actorId: Int,
    title: String,
    modifier: Modifier = Modifier,
    navController: NavHostController = LocalNavController.current,

    ) {
    val viewModel: ActorGalleryViewModel = koinViewModel(parameters = { parametersOf(actorId, title) })
    val uiState by viewModel.uiState.collectAsState()

    ActorGalleryContent(
        uiState = uiState,
        interactionListener = viewModel,
        modifier = modifier,
        title = uiState.actorName
    )
}

@Composable
fun ActorGalleryContent(
    uiState: ShowAllActorMoviesState,
    interactionListener: ActorGalleryInteractionListener,
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
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            MovieText(
                                text = uiState.error,
                                color = Theme.colors.shade.primary
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            MovieButton(
                                buttonText = stringResource(R.string.retry),
                                textColor = Theme.colors.button.primary,
                                textStyle = Theme.textStyle.title.small,
                                onClick = interactionListener::onRefresh
                            )
                        }
                    }
                }

                else -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        MovieAppBar(
                            title = title,
                            backButtonClick = interactionListener::backButtonClick,
                        )
                    CastGallery(
                        images = uiState.photos,
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

@CineVersePreviews
@Composable
fun ActorGalleryPreview(modifier: Modifier = Modifier) {
    ActorGalleryContent(
        uiState = ShowAllActorMoviesState(
            isLoading = false,
            error = null,
            photos = emptyList()
        ),
        interactionListener = object : ActorGalleryInteractionListener {
            override fun onRefresh() {}
            override fun backButtonClick() {}
        },
        modifier = modifier,
        title = "Actor Gallery"
    )
}
