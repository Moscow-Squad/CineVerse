package com.moscow.cineverse.screen.movie_details

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.rememberAsyncImagePainter
import com.example.design_system.R
import com.moscow.cineverse.designSystem.component.MovieAppBar
import com.moscow.cineverse.designSystem.component.MovieButton
import com.moscow.cineverse.designSystem.component.MovieCircularProgressBar
import com.moscow.cineverse.designSystem.component.MovieListSection
import com.moscow.cineverse.designSystem.component.MovieScaffold
import com.moscow.cineverse.designSystem.component.MovieText
import com.moscow.cineverse.designSystem.component.SectionTitle
import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.CastCard
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.MainMovieCard
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.MovieCardDetails
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.MovieRatingBottomSheet
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.MovieReviewCard
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.RatingSection
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.StaffInfoSection
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.StarCastSection
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.navigation.LocalNavController
import com.moscow.cineverse.navigation.routes.CollectionsBottomSheetRoute
import com.moscow.cineverse.navigation.routes.RecommendationsRoute
import com.moscow.cineverse.navigation.routes.ReviewsRoute
import com.moscow.cineverse.screen.castDetails.toFormattedBirthDate

import com.moscow.cineverse.screen.component.movie_poster_card.MoviePosterCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: MovieDetailsViewModel = koinViewModel(),
    navController: NavHostController = LocalNavController.current,
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(viewModel) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is MovieDetailsScreenEvents.NavigateBack -> {
                    navController.popBackStack()
                }

                is MovieDetailsScreenEvents.ShowError -> {
                    // TODO: Show error (Snackbar, Toast, etc.)
                }

                is MovieDetailsScreenEvents.NavigateToFullMovieList -> {
                    navController.navigate(RecommendationsRoute(event.movieID, event.movieTitle))
                }

                is MovieDetailsScreenEvents.NavigateToFullActors -> {

                }

                is MovieDetailsScreenEvents.NavigateToFullReviews -> {
                    navController.navigate(ReviewsRoute(event.movieID, true))

                }

                is MovieDetailsScreenEvents.AddToCollection -> {
                    navController.navigate(CollectionsBottomSheetRoute(event.movieId))
                }

                MovieDetailsScreenEvents.NavigateToFullCast -> {}
            }
        }
    }
    MovieDetailsContent(
        uiState = uiState,
        interactionListener = viewModel,
        onNavigateBack = { navController.popBackStack() },
        modifier = modifier,
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MovieDetailsContent(
    uiState: MovieScreenState,
    onNavigateBack: () -> Unit,
    interactionListener: MovieDetailsInteractionListener,
    modifier: Modifier = Modifier
) {
    val textColor = Theme.colors.shade.secondary

    val scrollState = rememberLazyListState()
    val isCollapsed by remember {
        derivedStateOf {
            scrollState.firstVisibleItemScrollOffset > 10 || scrollState.firstVisibleItemIndex > 0
        }
    }
    MovieScaffold {
        when {
            uiState.movieDetailsUi == null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    MovieCircularProgressBar(
                        gradientColors = listOf(
                            Theme.colors.brand.primary,
                            Theme.colors.brand.tertiary
                        )

                    )
                }
            }

            uiState.shouldShowError -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    MovieText(
                        text = "Error in loading movie details",
                        color = Theme.colors.shade.primary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    MovieButton(
                        buttonText = stringResource(com.moscow.cinverse.presentation.R.string.retry),
                        textColor = Theme.colors.button.primary,
                        textStyle = Theme.textStyle.title.small,
                        onClick = { }
                    )
                }
            }

            else -> {
                Column(modifier = modifier.background(Theme.colors.background.screen)) {
                    MovieAppBar(backButtonClick = onNavigateBack, showBackButton = true)
                    SharedTransitionLayout {
                        AnimatedContent(
                            targetState = isCollapsed,
                            label = "basic_transition"
                        ) { target ->
                            if (!target) {
                                uiState.movieDetailsUi?.let {
                                    MovieCardDetails(
                                        animatedVisibilityScope = this@AnimatedContent,
                                        sharedTransitionScope = this@SharedTransitionLayout,
                                        posterUrl = it.posterPath,
                                        title = uiState.movieDetailsUi.title,
                                        genres = uiState.movieDetailsUi.genres.joinToString(","),
                                        rating = uiState.movieDetailsUi.rating.toString(),
                                        duration = uiState.movieDetailsUi.duration.toHourMinuteFormat(),
                                        releaseDate = uiState.movieDetailsUi.releaseDate.toFormattedReleasedDate(),
                                        type = stringResource(com.moscow.cinverse.presentation.R.string.movie),
                                        onSaveClick = {
                                            interactionListener.onAddToCollection(
                                                uiState.movieDetailsUi.id
                                            )
                                        }
                                    )
                                }
                            } else {
                                uiState.movieDetailsUi?.let {
                                    MainMovieCard(
                                        posterUrl = it.posterPath,
                                        title = uiState.movieDetailsUi.title,
                                        animatedVisibilityScope = this@AnimatedContent,
                                        sharedTransitionScope = this@SharedTransitionLayout
                                    )
                                }
                            }
                        }
                    }

                    LazyColumn(
                        state = scrollState,
                        modifier = Modifier.background(Theme.colors.background.screen)
                    )
                    {
                        item {
                            Text(
                                text = stringResource(com.moscow.cinverse.presentation.R.string.storyline),
                                style = Theme.textStyle.title.small,
                                color = Theme.colors.shade.primary,
                                modifier = Modifier.padding(
                                    16.dp,
                                    top = 24.dp,
                                    bottom = 8.dp
                                ),
                            )
                        }
                        item {
                            Text(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                overflow = TextOverflow.Ellipsis,
                                text = buildAnnotatedString {
                                    withStyle(style = ParagraphStyle(lineHeight = 12.sp)) {
                                        withStyle(
                                            style = SpanStyle(
                                                color = textColor,
                                                fontWeight = FontWeight.Medium,
                                                fontSize = 12.sp,
                                                letterSpacing = 0.sp
                                            )
                                        ) {
                                            append(uiState.movieDetailsUi.description)
                                        }
                                    }
                                },
                                textAlign = TextAlign.Justify
                            )
                        }
                        item {
                            AnimatedVisibility(
                                !uiState.starCast.isNullOrEmpty()
                            ) {
                                StarCastSection(
                                    title = stringResource(com.moscow.cinverse.presentation.R.string.star_cast),
                                    modifier = Modifier
                                        .background(Theme.colors.background.screen)
                                        .padding(top = 24.dp, start = 16.dp, end = 16.dp),
                                    onSeeMoreClick = {},
                                    cast = uiState.starCast?.take(6) ?: emptyList(),
                                    castContent = { actor ->
                                        CastCard(
                                            castMember = actor,
                                            getOriginalName = { it.originalName },
                                            getCharacterName = { it.characterName },
                                            getProfileImage = { it.profileImage }
                                        )
                                    }


                                )
                            }
                        }
                        item {
                            StaffInfoSection(
                                staffInfo = listOf(
                                    "Characters" to uiState.characters.joinToString(","),
                                    "Director, Screenplay, Story" to uiState.director.joinToString(
                                        ","
                                    ),
                                    "Producer" to uiState.produce.joinToString(","),
                                    "Writer" to uiState.writer.joinToString(",")
                                ),
                                modifier = Modifier.padding(
                                    top = 24.dp,
                                    start = 16.dp,
                                    end = 16.dp
                                )
                            )
                        }

                        item {
                            AnimatedVisibility(uiState.recommendations.isNotEmpty()) {
                                MovieListSection(
                                    title = stringResource(com.moscow.cinverse.presentation.R.string.you_might_also_like),
                                    movies = uiState.recommendations,
                                    onClickShowMore = {
                                        interactionListener.onShowMoreRecommendations(
                                            uiState.movieDetailsUi.id,
                                            uiState.movieDetailsUi.title
                                        )
                                    },
                                    onClickPoster = { movie -> },
                                    modifier = Modifier.padding(top = 16.dp),
                                    movieCardContent = { movie, modifier, onClick ->
                                        MoviePosterCard(
                                            movie = movie,
                                            viewMode = ViewMode.GRID,
                                            showRating = true,
                                            onMovieClick = {},
                                            showGenres = false,
                                            showTitle = true,
                                            modifier = modifier,
                                            getTitleOverride = { it.title.take(15) + if (it.title.length > 15) "…" else "" }
                                        )
                                    }
                                )
                            }
                        }
                        item {
                            RatingSection(
                                icon = R.drawable.due_tone_star,
                                title = stringResource(com.moscow.cinverse.presentation.R.string.give_it_stars),
                                caption = stringResource(com.moscow.cinverse.presentation.R.string.let_the_world_know_how_you_felt),
                                onClickArrow = { interactionListener.showRatingBottomSheet() },
                                ratingStars = uiState.starsRating,
                                modifier = Modifier.padding(
                                    start = 16.dp,
                                    end = 16.dp,
                                    top = 24.dp
                                ),
                            )
                        }

                        if (!uiState.reviewsFlow.isNullOrEmpty()) {
                            item {

                                SectionTitle(
                                    title = stringResource(com.moscow.cinverse.presentation.R.string.top_reviews),
                                    onClick = {
                                        interactionListener.onShowMoreReviews(uiState.movieDetailsUi.id)
                                    },
                                    modifier = Modifier.padding(
                                        start = 16.dp,
                                        end = 16.dp,
                                        bottom = 12.dp,
                                        top = 24.dp
                                    )
                                )
                                repeat(uiState.reviewsFlow.size) {
                                    val userImage = uiState.reviewsFlow[it].userImage
                                    MovieReviewCard(
                                        uiState.reviewsFlow[it].name,
                                        uiState.reviewsFlow[it].username,
                                        uiState.reviewsFlow[it].reviewContent,
                                        uiState.reviewsFlow[it].rate,
                                        formatReviewDate(uiState.reviewsFlow[it].date),
                                        if (userImage.isEmpty()) null else rememberAsyncImagePainter(
                                            model = userImage
                                        ),
                                        modifier = Modifier.padding(
                                            start = 16.dp,
                                            end = 16.dp,
                                            bottom = 12.dp
                                        )
                                    )

                                }
                            }
                        }
                    }


                    MovieRatingBottomSheet(
                        isVisible = uiState.showRatingBottomSheet,
                        onDismiss = { interactionListener.onDismissOrCancelRatingBottomSheet() },
                        onRatingSubmit = { rating ->
                            interactionListener.onRatingSubmit(
                                rating,
                                uiState.movieDetailsUi.id
                            )
                        },
                        onRatingRemove = {
                            interactionListener.onRatingSubmit(
                                0,
                                uiState.movieDetailsUi.id
                            )
                        },
                        initialRating = uiState.starsRating,
                        hasExistingRating = uiState.starsRating != 0,
                        isLoading = uiState.isLoading
                    )


                }
            }
        }
    }
}

