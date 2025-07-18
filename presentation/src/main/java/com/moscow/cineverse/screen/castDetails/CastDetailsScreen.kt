package com.moscow.cineverse.screen.castDetails

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.moscow.cineverse.designSystem.component.InfoSection
import com.moscow.cineverse.designSystem.component.MovieAppBar
import com.moscow.cineverse.designSystem.component.MovieListSection
import com.moscow.cineverse.designSystem.component.MovieScaffold
import com.moscow.cineverse.designSystem.component.SectionTitle
import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.designSystem.component.cast_details.GallerySection
import com.moscow.cineverse.designSystem.component.cast_details.MainDetails
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.navigation.routes.CastBestOfMovieRoute
import com.moscow.cineverse.navigation.routes.CastGalleryRoute
import com.moscow.cineverse.navigation.routes.MovieDetailsRoute
import com.moscow.cineverse.screen.component.movie_poster_card.MoviePosterCard
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import com.moscow.cinverse.presentation.R as PresentationR
import androidx.core.net.toUri

@Composable
fun CastDetailsScreen(
    navController: NavHostController,
    actorId: Int,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CastDetailsViewModel = koinViewModel(parameters = { parametersOf(actorId) })
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(viewModel) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is CastDetailsEvent.NavigateBack -> onNavigateBack()
                is CastDetailsEvent.ShowError -> {
                    // TODO: Show error (Snackbar, Toast, etc.)
                }
                is CastDetailsEvent.OpenSocialMedia -> {
                    val intent = Intent(Intent.ACTION_VIEW, event.url.toUri())
                    context.startActivity(intent)
                }
                is CastDetailsEvent.NavigateToMovie -> {
                    navController.navigate(MovieDetailsRoute(event.movieId))
                }
                is CastDetailsEvent.NavigateToFullMovieList -> {
                    navController.navigate(CastBestOfMovieRoute(event.actorId, event.actorName))
                }
                is CastDetailsEvent.NavigateToFullGallery -> {
                    navController.navigate(CastGalleryRoute(event.actorId, event.actorName))
                }
            }
        }
    }

    MovieScaffold {
        CastDetailsContent(
            uiState = uiState,
            interactionListener = viewModel,
            onBackPressed = { viewModel.onBackPressed() },
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun CastDetailsContent(
    uiState: CastDetailsUiState,
    interactionListener: CastDetailsInteractionListener,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        MovieAppBar(
            title = uiState.actorDetails?.name ?: "",
            backButtonClick = { onBackPressed() },
            showBackButton = true,
            showAddButton = false,
            showLogo = false,
            showDivider = true
        )

        when {
            uiState.isLoading -> {
                LoadingContent(modifier = Modifier.weight(1f))
            }

            uiState.shouldShowError -> {
                ErrorContent(
                    errorMessage = uiState.errorMessage,
                    onRetry = { interactionListener.onRefresh() },
                    modifier = Modifier.weight(1f)
                )
            }

            uiState.isContentEmpty -> {
                EmptyContent(modifier = Modifier.weight(1f))
            }

            uiState.actorDetails != null -> {
                ActorDetailsContent(
                    uiState = uiState,
                    interactionListener = interactionListener,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
@Composable
private fun LoadingContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.material3.CircularProgressIndicator(
            color = Theme.colors.brand.primary
        )
    }
}

@Composable
private fun ErrorContent(
    errorMessage: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            androidx.compose.material3.Text(
                text = errorMessage,
                color = Theme.colors.shade.primary,
                style = Theme.textStyle.body.medium.regular,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomButton(
                text = stringResource(PresentationR.string.retry),
                onClick = onRetry
            )
        }
    }
}

@Composable
private fun EmptyContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.material3.Text(
            text = stringResource(PresentationR.string.no_content_available),
            color = Theme.colors.shade.primary,
            style = Theme.textStyle.body.medium.regular
        )
    }
}

@Composable
private fun CustomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(Theme.radius.large))
            .background(Theme.colors.brand.primary)
            .clickable { onClick() }
            .padding(horizontal = 24.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.material3.Text(
            text = text,
            color = Color.White,
            style = Theme.textStyle.body.medium.regular
        )
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
            uiState.actorDetails?.let { actorDetails ->
                MainDetails(
                    profileImage = actorDetails.profileImg,
                    name = actorDetails.name,
                    date = actorDetails.birthDate.toFormattedBirthDate(),
                    location = actorDetails.placeOfBirth,
                    scrollState = null,
                    socialMediaLinks = uiState.socialMediaLinks,
                    onSocialMediaClick = { platform, url ->
                        interactionListener.onSocialMediaClick(platform, url)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        item {
            if (uiState.movies.isNotEmpty()) {
                MovieListSection(
                    title = "Best of ${uiState.actorDetails?.name ?: ""}",
                    movies = uiState.movies,
                    paddingHorizontal = 20,
                    onClickShowMore = {
                        interactionListener.onShowMoreMovies()
                    },
                    onClickPoster = { movie ->
                        interactionListener.onMovieClick(movie)
                    },
                    movieCardContent = { movie, cardModifier, onMovieClick ->
                        MoviePosterCard(
                            movie = movie.toMediaItemUi(),
                            viewMode = ViewMode.GRID,
                            showRating = true,
                            onMovieClick = { movieId -> onMovieClick(movie) },
                            showGenres = false,
                            showTitle = true,
                            modifier = cardModifier,
                            getTitleOverride = { it.title.take(15) + if (it.title.length > 15) "…" else "" }
                        )
                    },
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        item {
            if (uiState.images.isNotEmpty() && uiState.images.size >= 3) {
                Column(
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    SectionTitle(
                        title = "Gallery",
                        onClick = {
                            interactionListener.onShowMoreGallery()
                        },
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    GallerySection(
                        images = uiState.images.take(3),
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                }
            }
        }

        item {
            uiState.actorDetails?.let { actorDetails ->
                if (actorDetails.biography.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                    ) {
                        InfoSection(
                            title = "Biography",
                            description = actorDetails.biography,
                            showGenres = false,
                            maxDescriptionLines = Int.MAX_VALUE,
                            paddingBetween = 8.dp,
                            modifier = Modifier.padding(16.dp),
                            rating = 1.5f
                        )
                    }
                }
            }
        }
    }
}