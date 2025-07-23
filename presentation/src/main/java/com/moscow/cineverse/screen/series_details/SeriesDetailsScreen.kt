package com.moscow.cineverse.screen.series_details

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
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
import androidx.compose.foundation.lazy.items
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
import com.moscow.cineverse.component.MoviePosterCard
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
import com.moscow.cineverse.screen.series_details.component.SeasonCard
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.StaffInfoSection
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.StarCastSection
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.navigation.LocalNavController
import com.moscow.cineverse.navigation.routes.CollectionsBottomSheetRoute
import com.moscow.cineverse.navigation.routes.ReviewsRoute
import com.moscow.cineverse.navigation.routes.SeriesRecommendationRoute
import com.moscow.cineverse.navigation.routes.SeriesSeasonsRoute
import com.moscow.cineverse.screen.movie_details.formatReviewDate
import com.moscow.cinverse.presentation.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun SeriesDetailsScreen(
    viewModel: SeriesDetailsViewModel = koinViewModel(),
    navController: NavHostController = LocalNavController.current
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is SeriesDetailsScreenEffects.AddToCollection -> {
                    navController.navigate(
                        CollectionsBottomSheetRoute(mediaItemId = event.seriesId)
                    )
                }
                is SeriesDetailsScreenEffects.NavigateToRecommendationSeries -> {
                    navController.navigate(SeriesRecommendationRoute(event.seriesId))
                }
                is SeriesDetailsScreenEffects.NavigateToReviewsScreen -> {
                    navController.navigate(ReviewsRoute(event.seriesId, false))
                }
                is SeriesDetailsScreenEffects.NavigateToSeriesSeasonsScreen -> {
                    navController.navigate(SeriesSeasonsRoute(event.seriesId))
                }
            }
        }
    }
    SeriesDetailsContent(uiState = uiState, interactionListener = viewModel)
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SeriesDetailsContent(
    uiState: SeriesDetailsScreenState,
    interactionListener: SeriesInteractionListener,
) {
    val detail = uiState.seriesDetail
    val textColor = Theme.colors.shade.secondary
    val scrollState = rememberLazyListState()
    val isCollapsed by remember {
        derivedStateOf {
            scrollState.firstVisibleItemScrollOffset > 10 || scrollState.firstVisibleItemIndex > 0
        }
    }
    MovieScaffold{
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                MovieCircularProgressBar(gradientColors = listOf(Theme.colors.brand.primary, Theme.colors.brand.tertiary))
            }
        }
        else if (uiState.errorMessage != ""){
            Column(
                modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                MovieText(
                    text = "Error in loading movie details",
                    color = Theme.colors.shade.primary
                )
                Spacer(modifier = Modifier.height(16.dp))
                MovieButton(
                    buttonText = stringResource(R.string.retry),
                    textColor = Theme.colors.button.primary,
                    textStyle = Theme.textStyle.title.small,
                    onClick = { }
                )
            }
        }
        else {
            Column(modifier = Modifier.background(Theme.colors.background.screen)) {
                MovieAppBar(backButtonClick = {}, showBackButton = true)
                SharedTransitionLayout {
                    AnimatedContent(
                        targetState = isCollapsed,
                        label = "basic_transition"
                    ) { target ->
                        if (!target) {
                            MovieCardDetails(
                                posterUrl = detail.posterPath,
                                title = detail.title,
                                genres = detail.genre,
                                rating = detail.rating,
                                duration = detail.duration,
                                releaseDate = detail.releaseDate,
                                type = detail.type,
                                animatedVisibilityScope = this@AnimatedContent,
                                sharedTransitionScope = this@SharedTransitionLayout,
                                onSaveClick = { interactionListener.addToCollection() }
                            )
                        } else {
                            MainMovieCard(
                                posterUrl = detail.posterPath,
                                title = detail.title,
                                animatedVisibilityScope = this@AnimatedContent,
                                sharedTransitionScope = this@SharedTransitionLayout
                            )
                        }
                    }
                }
                LazyColumn(
                    state = scrollState,
                    modifier = Modifier.background(Theme.colors.background.screen)
                ){
                    item {
                        Text(
                            text = stringResource(R.string.storyline),
                            style = Theme.textStyle.title.small,
                            color = Theme.colors.shade.primary,
                            modifier = Modifier.padding(16.dp, top = 24.dp, bottom = 8.dp),
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
                                        append(detail.overview)
                                    }
                                }
                            },
                            textAlign = TextAlign.Justify
                        )
                    }
                    item {
                        SectionTitle(
                            title = "Latest Seasons",
                            onClick = {interactionListener.onShowMoreSeasonsClicked(uiState.seriesDetail.id)},
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
                            modifier = Modifier.padding(horizontal = 16.dp),
                            seasonNumber = season.title,
                            episodeCount = season.episodeCount,
                            airDate = season.airDate,
                            posterUrl = season.posterPath,
                            caption = season.overview,
                            rate = season.rate
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
                                castContent = {actor->
                                    CastCard(
                                        modifier = Modifier.clickable{},
                                        castMember = actor,
                                        getOriginalName = { it.name },
                                        getCharacterName = { it.characterName },
                                        getProfileImage = { it.profilePath }
                                    )
                                }
                            )
                        }
                    }
                    if (uiState.crew.isNotEmpty() || detail.creators.isNotEmpty()) {
                        item {
                            val staffInfo = mutableListOf<Pair<String, String>>()

                            detail.creators.forEach { creator ->
                                staffInfo.add(creator.job to creator.name)
                            }
                            uiState.crew.groupBy { it.job }
                                .mapValues { it.value.map { member -> member.name } }
                                .forEach { (job, names) ->
                                    staffInfo.add(job to names.joinToString(", "))
                                }
                            StaffInfoSection(
                                staffInfo = staffInfo.take(5),
                                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp)
                            )
                        }
                    }
                    if (uiState.recommendation.isNotEmpty()){
                        item {
                            MovieListSection(
                                title = stringResource(R.string.you_might_also_like),
                                movies = uiState.recommendation.take(6),
                                onClickShowMore = {interactionListener.onShowMoreRecommendationsClicked(uiState.seriesDetail.id)},
                                onClickPoster = { series -> },
                                modifier = Modifier.padding(top = 16.dp),
                                movieCardContent = { series, modifier, onClick ->
                                    MoviePosterCard(
                                        movie = series,
                                        viewMode = ViewMode.GRID,
                                        showRating = true,
                                        onMovieClick = {},
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
                            icon = Theme.icons.dueTone.star,
                            title = stringResource(R.string.give_it_stars),
                            caption = stringResource(R.string.let_the_world_know_how_you_felt),
                            onClickArrow = interactionListener::showRatingBottomSheet,
                            ratingStars = uiState.starsRating,
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp),
                        )
                    }
                    if (uiState.reviews.isNotEmpty()) {
                        item {
                            SectionTitle(
                                title = stringResource(R.string.top_reviews),
                                onClick = {interactionListener.onShowMoreReviewsClicked(uiState.seriesDetail.id)},
                                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 12.dp, top = 24.dp)
                            )
                        }
                        items(uiState.reviews.take(3)) { review ->
                            MovieReviewCard(
                                name = review.username,
                                username = "@${review.username}",
                                reviewText = review.reviewContent,
                                rating = review.rate.toInt(),
                                date = formatReviewDate(review.date),
                                avatar =  if (review.userImage.isEmpty()) null else rememberAsyncImagePainter(
                                    model = review.userImage
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
                onDismiss = interactionListener::onDismissOrCancelRatingBottomSheet,
                onRatingSubmit = { rating -> interactionListener.onRatingSubmit(rating, detail.id) },
                onRatingRemove = { interactionListener.onRatingSubmit(0, detail.id) },
                initialRating = uiState.starsRating,
                hasExistingRating = uiState.starsRating != 0,
            )
        }
    }
}