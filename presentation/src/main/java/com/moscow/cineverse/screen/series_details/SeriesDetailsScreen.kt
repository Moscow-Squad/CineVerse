package com.moscow.cineverse.screen.series_details

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.design_system.R
import com.moscow.cineverse.designSystem.component.MovieAppBar
import com.moscow.cineverse.designSystem.component.MovieCard
import com.moscow.cineverse.designSystem.component.MovieListSection
import com.moscow.cineverse.designSystem.component.SectionTitle
import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.CastMember
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.MainMovieCard
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.MovieCardDetails
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.MovieRatingBottomSheet
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.MovieReviewCard
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.RatingSection
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.Season
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.SeasonCard
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.StaffInfoSection
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.StarCastSection
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.navigation.routes.CollectionsBottomSheetRoute
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun SeriesDetailsScreen(
    viewModel: SeriesDetailsViewModel = koinViewModel(),
    navController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is SeriesDetailsEvents.AddToCollection -> {
                    navController.navigate(
                        CollectionsBottomSheetRoute(mediaItemId = event.seriesId)
                    )
                }
            }
        }
    }
    SeriesDetailsContent(
        uiState = uiState,
        interactionListener = viewModel
        uiState,
        onClickArrow = viewModel::showRatingBottomSheet,
        onDismiss = viewModel::onDismissOrCancelRatingBottomSheet,
        onRatingSubmit = viewModel::onRatingSubmit
    )
}

// Helper functions for date formatting
private fun formatDate(dateString: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy, MMM dd", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        date?.let { outputFormat.format(it) } ?: dateString
    } catch (e: Exception) {
        dateString
    }
}

private fun formatReviewDate(dateString: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        date?.let { outputFormat.format(it) } ?: dateString
    } catch (e: Exception) {
        dateString
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SeriesDetailsContent(
    uiState: SeriesDetailsUiState,
    interactionListener: SeriesInteractionListener
    onClickArrow: () -> Unit,
    onDismiss: () -> Unit,
    onRatingSubmit: (Int, Int) -> Unit
) {
    val detail = uiState.seriesDetail
    val reviews = uiState.reviews
    val isLoading = uiState.isLoading
    val error = uiState.error
    val latestSeason = uiState.latestSeason
    val listOfSeries = uiState.listOfSeries
    val textColor = Theme.colors.shade.secondary
    val scrollState = rememberLazyListState()
    val isCollapsed by remember {
        derivedStateOf {
            scrollState.firstVisibleItemScrollOffset > 10 || scrollState.firstVisibleItemIndex > 0
        }
    }

    Column {
        MovieAppBar()
        SharedTransitionLayout {
            AnimatedContent(
                targetState = isCollapsed,
                label = "basic_transition"
            ) { target ->
                if (!target) {
                    uiState.seriesDetail?.let { detail ->
                        MovieCardDetails(
                            posterUrl = detail.posterPath?.let { "https://image.tmdb.org/t/p/w500$it" }
                                ?: "",
                            title = detail.title,
                            genres = detail.genres.joinToString(", ") { it.name },
                            rating = detail.rating.toString(),
                            duration = detail.runtime ?: "N/A",
                            releaseDate = detail.releaseDate?.let { formatDate(it) } ?: "",
                            type = detail.type,
                            animatedVisibilityScope = this@AnimatedContent,
                            sharedTransitionScope = this@SharedTransitionLayout,
                            onSaveClick = {
                                Log.e(
                                    "kllvmv",
                                    "addToCollection:",
                                )

                                interactionListener.addToCollection()
                            }
                        )
                    }
                } else {
                    MainMovieCard(
                        posterUrl = detail?.posterPath?.let { "https://image.tmdb.org/t/p/w500$it" }
                            ?: "",
                        title = detail?.title ?: "Loading...",
                        animatedVisibilityScope = this@AnimatedContent,
                        sharedTransitionScope = this@SharedTransitionLayout,
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.colors.background.screen)
        )
        {

            if (isLoading && detail == null) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Theme.colors.brand.primary
                )
            } else {
                LazyColumn(
                    state = scrollState,
                    modifier = Modifier.background(Theme.colors.background.screen)
                ) {

                    item {
                        Text(
                            text = "Storyline",
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
                                        append(detail?.overview ?: "No overview available.")
                                    }
                                }
                            },
                            textAlign = TextAlign.Justify
                        )
                    }
                    item {
                        SectionTitle(
                            title = "Latest Seasons",
                            onClick = {},
                            modifier = Modifier.padding(
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 12.dp,
                                top = 24.dp
                            )
                        )
                    }
                    if (detail?.numberOfSeasons != null && detail.numberOfSeasons!! > 0) {
                        items(latestSeason.size) {
                            SeasonCard(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                season = Season(
                                    seasonNumber = detail.numberOfSeasons!!,
                                    episodeCount = detail.numberOfEpisodes ?: 0,
                                    airDate = detail.releaseDate?.substring(0, 4) ?: "N/A",
                                    posterUrl = detail.posterPath?.let { "https://image.tmdb.org/t/p/w500$it" }
                                        ?: "",
                                    caption = "Season ${detail.numberOfSeasons} of the series",
                                    rate = detail.rating.toFloat()
                                )
                            )
                        }
                    }
                    if (detail?.cast?.isNotEmpty() == true) {
                        items(detail.cast) {
                            StarCastSection(
                                title = "Star Cast",
                                cast = detail.cast.take(5).map { cast ->
                                    CastMember(
                                        realName = cast.name,
                                        nameInMovie = cast.character ?: "Unknown",
                                        imageUrl = cast.profilePath?.let { "https://image.tmdb.org/t/p/w500$it" }

                                    )
                                },
                                onSeeMoreClick = {},
                                castContent = { }
                            )
                        }
                    }
                    if (detail?.creators?.isNotEmpty() == true) {
                        item {
                            val staffInfo = mutableListOf<Pair<String, String>>()
                            detail.creators.forEach { creator ->
                                staffInfo.add("Creator" to creator.name)
                            }

                            StaffInfoSection(
                                staffInfo = staffInfo,
                                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp)
                            )
                        }
                    }

                    // Since recommendations are not in the new model, using hardcoded data as in original
                    items(listOfSeries.size) {
                        MovieListSection(
                            title = "You Might Also Like",
                            movies = listOfSeries,
                            onClickShowMore = { },
                            onClickPoster = { },
                            modifier = Modifier.padding(top = 16.dp),
                            movieCardContent = { movie, _, onClick ->
                                MovieCard(
                                    movieData = movie,
                                    viewMode = ViewMode.GRID,
                                    onMovieClick = { onClick(movie) },
                                    getId = { it.id },
                                    getTitle = { it.title },
                                    getPosterUrl = { it.posterPath.toString() },
                                    getRating = { it.rating.toFloat() },
                                    getGenres = { listOf() },
                                    getDuration = { it.runtime ?: "" },
                                    getReleaseDate = {
                                        it.releaseDate?.let { date -> formatDate(date) } ?: ""
                                    }
                                )
                            }
                        )

                    }

                    item {
                        RatingSection(
                            icon = R.drawable.due_tone_star,
                            title = "Give it Stars! ",
                            caption = "Let the world know how you felt.",
                            onClickArrow = onClickArrow,
                            modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp)
                        )
                    }

                    if (reviews.isNotEmpty()) {
                        item {
                            SectionTitle(
                                title = "Top Reviews",
                                onClick = {},
                                modifier = Modifier.padding(
                                    start = 16.dp,
                                    end = 16.dp,
                                    bottom = 12.dp,
                                    top = 24.dp
                                )
                            )
                        }

                        items(reviews) { review ->
                            MovieReviewCard(
                                review.author,
                                "@${review.username}",
                                review.content,
                                review.rating.toInt(),
                                formatReviewDate(review.createdAt),
                                painterResource(R.drawable.outline_user),
                                modifier = Modifier.padding(
                                    start = 16.dp,
                                    end = 16.dp,
                                    bottom = 12.dp
                                )
                            )
                        }
                    }

                    if (error != null && detail == null) {
                        item {
                            Text(
                                "Error: $error",
                                color = Theme.colors.brand.secondary,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }

                MovieRatingBottomSheet(
                    isVisible = uiState.showRatingBottomSheet,
                    onDismiss = onDismiss,
                    onRatingSubmit = { rating ->
                        onRatingSubmit(rating, detail?.id ?: 0)
                    },
                    onRatingRemove = {
                        onRatingSubmit(0, detail?.id ?: 0)
                    },
                    initialRating = uiState.starsRating,
                    hasExistingRating = uiState.starsRating != 0,
                )


            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun SeriesDetailsScreenPreview() {
//    CineVerseTheme {
//        SeriesDetailsContent(
//            uiState = SeriesDetailsUiState(
//                isLoading = false,
//                seriesDetail = SeriesDetail(
//                    id = 101,
//                    title = "The Great Adventure",
//                    posterPath = "",
//                    backdropPath = "",
//                    genres = listOf(
//                        Genre(id = 1, name = "Adventure"),
//                        Genre(id = 2, name = "Drama")
//                    ),
//                    rating = 8.5,
//                    voteCount = 1245,
//                    runtime = "45m per episode",
//                    releaseDate = "2021-09-15",
//                    type = "SERIES",
//                    overview = "A thrilling adventure series that explores the life of a young hero on a quest to save their world.",
//                    numberOfSeasons = 3,
//                    numberOfEpisodes = 30,
//                    cast = listOf(
//                        com.android.domain.model.details.CastMember(
//                            id = 1,
//                            name = "Emma Stone",
//                            character = "Hero",
//                            profilePath = null
//                        ),
//                        com.android.domain.model.details.CastMember(
//                            id = 2,
//                            name = "Ryan Gosling",
//                            character = "Mentor",
//                            profilePath = null
//                        )
//                    ),
//                    creators = listOf(
//                        Creator(
//                            id = 1,
//                            name = "John Doe",
//                            profilePath = null
//                        )
//                    ),
//                    tagline = "",
//                    status = "",
//                    lastAirDate = null,
//                    nextAirDate = null,
//                    lastEpisodeToAir = null,
//                    nextEpisodeToAir = null,
//                    seasons = emptyList(),
//                    similarSeries = emptyList(),
//                    reviews = emptyList()
//                ),
//                reviews = listOf(
//                    Review(
//                        id = "rev1",
//                        author = "FilmFan99",
//                        username = "filmfan99",
//                        content = "Absolutely amazing! The plot, the acting, everything was top-notch.",
//                        rating = 9.0,
//                        createdAt = "2024-03-05T15:23:01.000Z",
//                        avatarPath = "null"
//                    ),
//                    Review(
//                        id = "rev2",
//                        author = "MovieBuff88",
//                        username = "moviebuff88",
//                        content = "Great cinematography and storytelling. Can't wait for the next season!",
//                        rating = 8.0,
//                        createdAt = "2024-04-10T11:12:01.000Z",
//                        avatarPath = "null"
//                    )
//                )
//            )
//        )
//    }
//}
