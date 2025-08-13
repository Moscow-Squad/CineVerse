package com.moscow.cineverse.screen.series_details

import android.content.Intent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.rememberAsyncImagePainter
import com.moscow.cineverse.component.MovieListSection
import com.moscow.cineverse.component.ErrorContent
import com.moscow.cineverse.component.MoviePosterCard
import com.moscow.cineverse.component.SectionTitle
import com.moscow.cineverse.component.StorylineSection
import com.moscow.cineverse.designSystem.component.app_bar.MovieAppBar
import com.moscow.cineverse.designSystem.component.bottomsheet.CineVerseBottomSheet
import com.moscow.cineverse.designSystem.component.card.MessageInfoCard
import com.moscow.cineverse.designSystem.component.indicator.MovieCircularProgressBar
import com.moscow.cineverse.designSystem.component.wrapper.MovieScaffold
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.mapper.formatReviewDate
import com.moscow.cineverse.mapper.toHourMinuteFormat
import com.moscow.cineverse.screen.movieSeriesDetails.CastCard
import com.moscow.cineverse.screen.movieSeriesDetails.MediaHeader
import com.moscow.cineverse.screen.movieSeriesDetails.MovieRatingBottomSheet
import com.moscow.cineverse.screen.movieSeriesDetails.MovieReviewCard
import com.moscow.cineverse.screen.movieSeriesDetails.RatingSection
import com.moscow.cineverse.screen.movieSeriesDetails.StaffInfoSection
import com.moscow.cineverse.screen.movieSeriesDetails.StarCastSection
import com.moscow.cineverse.screen.series_details.component.SeasonCard
import com.moscow.cinverse.presentation.R

@Composable
fun SeriesDetailsScreen(
    viewModel: SeriesDetailsScreenScreenViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToCollectionBottomSheet: (Int) -> Unit,
    navigateToSeriesRecommendation: (Int, String) -> Unit,
    navigateToReviews: (Int) -> Unit,
    navigateToSeriesSeasons: (Int) -> Unit,
    navigateToCastDetails: (Int) -> Unit,
    navigateToSeriesDetails: (Int) -> Unit,
    navigateToLogin: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(viewModel) {
        viewModel.uiEffect.collect { event ->
            when (event) {
                is SeriesDetailsScreenEffects.AddToCollection -> {
                    navigateToCollectionBottomSheet(event.seriesId)
                }

                is SeriesDetailsScreenEffects.NavigateToRecommendationSeries -> {
                    navigateToSeriesRecommendation(event.seriesId, event.seriesName)
                }

                is SeriesDetailsScreenEffects.NavigateToReviewsScreen -> {
                    navigateToReviews(event.seriesId)
                }

                is SeriesDetailsScreenEffects.NavigateToSeriesSeasonsScreen -> {
                    navigateToSeriesSeasons(event.seriesId)
                }

                is SeriesDetailsScreenEffects.NavigateToActorDetailsScreen -> {
                    navigateToCastDetails(event.ActorId)
                }

                is SeriesDetailsScreenEffects.NavigateToSeriesDetailsScreen -> {
                    navigateToSeriesDetails(event.seriesId)
                }

                is SeriesDetailsScreenEffects.OpenTrailer -> {
                    val intent = Intent(Intent.ACTION_VIEW, event.url.toUri())
                    context.startActivity(intent)
                }

                is SeriesDetailsScreenEffects.NavigateToLogin -> {
                    navigateToLogin()
                }
            }
        }
    }
    SeriesDetailsContent(
        uiState = uiState,
        interactionListener = viewModel,
        onNavigateBack = navigateBack,
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SeriesDetailsContent(
    uiState: SeriesDetailsScreenState,
    interactionListener: SeriesDetailsScreenInteractionListener,
    onNavigateBack: () -> Unit,
) {
    val detail = uiState.seriesDetail
    val scrollState = rememberLazyListState()
    LaunchedEffect(key1 = Unit) {
        scrollState.animateScrollToItem(0)
    }
    MovieScaffold {
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Theme.colors.background.screen),
                    contentAlignment = Alignment.Center
                ) {
                    MovieCircularProgressBar()
                }
            }

            uiState.shouldShowError -> {
                ErrorContent(
                    errorMessage = uiState.errorMessage,
                    onRetry = interactionListener::onRetry,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Theme.colors.background.screen)
                )
            }

            else -> {
                LazyColumn(
                    state = scrollState,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Theme.colors.background.screen)
                ) {
                    // Make the header section sticky
                    stickyHeader {
                        Column(
                            modifier = Modifier.background(Theme.colors.background.screen)
                        ) {
                            MovieAppBar(backButtonClick = onNavigateBack, showBackButton = true)
                            uiState.seriesDetail.let {
                                MediaHeader(
                                    scrollState = scrollState,
                                    enableBlur = uiState.enableBlur,
                                    posterUrl = it.posterPath,
                                    title = it.title,
                                    genres = it.genre,
                                    rating = it.rating,
                                    duration = it.duration.toHourMinuteFormat(LocalContext.current),
                                    releaseDate = it.releaseDate,
                                    type = stringResource(com.moscow.cineverse.design_system.R.string.series_type),
                                    onSaveClick = interactionListener::addToCollection,
                                    isSaveEnabled = false,
                                    onPlayClick = interactionListener::onPlayButtonClicked,
                                )
                            }
                        }
                    }

                    item {
                        StorylineSection(description = uiState.seriesDetail.overview)
                    }
                    item {
                        SectionTitle(
                            title = stringResource(R.string.latest_seasons),
                            onClick = { interactionListener.onShowMoreSeasonsClicked(uiState.seriesDetail.id) },
                            modifier = Modifier.padding(
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 12.dp,
                                top = 24.dp
                            )
                        )
                    }
                    items(detail.seasons.reversed().take(3)) { season ->
                        SeasonCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = 8.dp
                                ),
                            seasonNumber = season.title,
                            episodeCount = season.episodeCount,
                            airDate = season.airDate,
                            posterUrl = season.posterPath,
                            caption = season.overview,
                            rate = season.rate,
                            enableBlur = uiState.enableBlur,
                        )
                    }
                    if (uiState.cast.isNotEmpty()) {
                        item {
                            StarCastSection(
                                title = stringResource(R.string.star_cast),
                                modifier = Modifier
                                    .background(Theme.colors.background.screen)
                                    .padding(top = 24.dp, start = 16.dp, end = 16.dp),
                                cast = uiState.cast.take(10),
                                castContent = { actor ->
                                    CastCard(
                                        modifier = Modifier.clickable {
                                            interactionListener.onActorClicked(
                                                actor.id
                                            )
                                        },
                                        castMember = actor,
                                        enableBlur = uiState.enableBlur,
                                        getOriginalName = { it.originalName },
                                        getCharacterName = { it.characterName },
                                        getProfileImage = { it.profileImage }
                                    )
                                }
                            )
                        }
                    }
                    if (uiState.crew.isNotEmpty() || detail.creators.isNotEmpty()) {
                        item {
                            StaffInfoSection(
                                staffInfo = listOf(
                                    stringResource(R.string.characters) to uiState.characters.joinToString(
                                        ","
                                    ),
                                    stringResource(R.string.director_screenplay_story) to uiState.director.joinToString(
                                        ","
                                    ),
                                    stringResource(R.string.producer) to uiState.produce.joinToString(
                                        ","
                                    ),
                                    stringResource(R.string.writer) to uiState.writer.joinToString(
                                        ","
                                    )
                                ),
                                modifier = Modifier.padding(
                                    start = 16.dp,
                                    end = 16.dp,
                                    top = 24.dp
                                )
                            )
                        }
                    }
                    if (uiState.recommendation.isNotEmpty()) {
                        item {
                            MovieListSection(
                                title = stringResource(R.string.you_might_also_like),
                                movies = uiState.recommendation.take(6),
                                onClickShowMore = {
                                    interactionListener.onShowMoreRecommendationsClicked(
                                        uiState.seriesDetail.id,
                                        uiState.seriesDetail.title
                                    )
                                },
                                onClickPoster = { series -> },
                                modifier = Modifier.padding(top = 16.dp),
                                paddingHorizontal = 16,
                                movieCardContent = { series, modifier, onClick ->
                                    MoviePosterCard(
                                        movie = series,
                                        onMovieClick = {
                                            interactionListener.onSeriesClicked(
                                                series.id
                                            )
                                        },
                                        modifier = modifier,
                                        enableBlur = uiState.enableBlur,
                                    )
                                }
                            )
                        }
                    }
                    item {
                        RatingSection(
                            icon = R.drawable.due_tone_star,
                            title = stringResource(R.string.give_it_stars),
                            caption = stringResource(R.string.let_the_world_know_how_you_felt),
                            onClick = interactionListener::showRatingBottomSheet,
                            ratingStars = uiState.starsRating,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
                        )
                    }
                    if (uiState.reviews.isNotEmpty()) {
                        item {
                            SectionTitle(
                                title = stringResource(R.string.top_reviews),
                                onClick = { interactionListener.onShowMoreReviewsClicked(uiState.seriesDetail.id) },
                                modifier = Modifier.padding(
                                    start = 16.dp,
                                    end = 16.dp,
                                    bottom = 12.dp
                                )
                            )
                        }
                        items(uiState.reviews.take(3)) { review ->
                            MovieReviewCard(
                                name = review.username,
                                username = review.username,
                                reviewText = review.reviewContent,
                                rating = review.rate.toInt(),
                                date = formatReviewDate(review.date),
                                avatar = if (review.userImageUrl.isEmpty()) null else rememberAsyncImagePainter(
                                    model = review.userImageUrl
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

                MovieRatingBottomSheet(
                    isVisible = uiState.showRatingBottomSheet,
                    onDismiss = interactionListener::onDismissOrCancelRatingBottomSheet,
                    onRatingSubmit = { rating ->
                        interactionListener.onRatingSubmit(
                            rating,
                            detail.id
                        )
                    },
                    onRatingRemove = { interactionListener.onDeleteRatingSeries(detail.id) },
                    initialRating = uiState.starsRating,
                    hasExistingRating = uiState.starsRating != 0,
                    isLoading = uiState.isLoading
                )

                if (uiState.showLoginBottomSheet) {
                    CineVerseBottomSheet(
                        title = stringResource(R.string.you_re_almost_there),
                        onClose = { interactionListener.onDismissLoginBottomSheet() },
                        onDismissRequest = { interactionListener.onDismissLoginBottomSheet() },
                        showCancelIcon = true,
                        onAddNewCollectionClick = {}
                    ) {
                        MessageInfoCard(
                            title = stringResource(R.string.you_re_almost_there),
                            description = stringResource(R.string.log_in_to_save_movies_create_collections_and_get_personalized_recommendations),
                            icon = painterResource(R.drawable.due_tone_video_library),
                            showButtonsGroup = true,
                            firstButtonText = stringResource(R.string.not_now),
                            onClickFirstButton = { interactionListener.onDismissLoginBottomSheet() },
                            secondButtonText = stringResource(R.string.log_in),
                            onClickSecondButton = { interactionListener.navigateToLogin() },
                        )
                    }
                }
            }
        }
    }
}