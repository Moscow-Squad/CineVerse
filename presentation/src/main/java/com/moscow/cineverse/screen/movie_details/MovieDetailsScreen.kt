package com.moscow.cineverse.screen.movie_details

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.design_system.R
import com.moscow.cineverse.designSystem.component.Movie
import com.moscow.cineverse.designSystem.component.MovieAppBar
import com.moscow.cineverse.designSystem.component.MovieListSection
import com.moscow.cineverse.designSystem.component.SectionTitle
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.MainMovieCard
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.MovieCardDetails
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.MovieReviewCard
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.RatingSection
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.StaffInfoSection
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: MovieDetailsViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    MovieDetailsContent(
        uiState,
        modifier
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MovieDetailsContent(
    uiState: MovieScreenState,
    modifier: Modifier = Modifier
) {
    val textColor = Theme.colors.shade.secondary
    val scrollState = rememberLazyListState()
    val isCollapsed by remember {
        derivedStateOf {
            scrollState.firstVisibleItemScrollOffset > 10 || scrollState.firstVisibleItemIndex > 0
        }
    }
    Column {
        MovieAppBar()
        when {
            uiState.movieDetailsUi != null -> {
                SharedTransitionLayout {
                    AnimatedContent(
                        targetState = isCollapsed,
                        label = "basic_transition"
                    ) { target ->
                        if (!target) {
                            MovieCardDetails(
                                posterUrl = uiState.movieDetailsUi.posterPath,
                                title = uiState.movieDetailsUi.title,
                                genres = uiState.movieDetailsUi.genres.joinToString(),
                                rating = uiState.movieDetailsUi.rating.toString(),
                                duration = uiState.movieDetailsUi.duration,
                                releaseDate = uiState.movieDetailsUi.releaseDate,
                                type = "MOVIE",
                                animatedVisibilityScope = this@AnimatedContent,
                                sharedTransitionScope = this@SharedTransitionLayout
                            )
                        } else {
                            MainMovieCard(
                                posterUrl = uiState.movieDetailsUi.posterPath,
                                title = uiState.movieDetailsUi.title,
                                animatedVisibilityScope = this@AnimatedContent,
                                sharedTransitionScope = this@SharedTransitionLayout
                            )
                        }
                    }
                }

                Log.d("TAG", "MovieDetailsContent:${uiState.movieDetailsUi} ")
                LazyColumn(
                    state = scrollState,
                    modifier = Modifier.background(Theme.colors.background.screen)

                ) {

                    item {
                        Text(
                            text = stringResource(com.moscow.cinverse.presentation.R.string.storyline),
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
                                        append(uiState.movieDetailsUi.description)
                                    }
                                }
                            },
                            textAlign = TextAlign.Justify
                        )
                    }
//                    item {
//                        StarCastSection(
//                            modifier = Modifier
//                                .background(Theme.colors.background.screen)
//                                .padding(top = 24.dp, start = 16.dp, end = 16.dp),
//                            seeMore = {},
//                            cast = listOf(
//                                CastMember(
//                                    realName = "John Doe",
//                                    nameInMovie = "John",
//                                    imageUrl = "https://wror.com/uploads/2025/05/GettyImages-1511406162.jpg?format=auto&optimize=high&width=1440"
//                                ),
//                                CastMember(
//                                    realName = "Jane Smith",
//                                    nameInMovie = "Jane",
//                                    imageUrl = "https://wror.com/uploads/2025/05/GettyImages-1511406162.jpg?format=auto&optimize=high&width=1440"
//                                ),
//                                CastMember(
//                                    realName = "Alice Johnson",
//                                    nameInMovie = "Alice",
//                                    imageUrl = "https://wror.com/uploads/2025/05/GettyImages-1511406162.jpg?format=auto&optimize=high&width=1440"
//                                ),
//                                CastMember(
//                                    realName = "Bob Brown",
//                                    nameInMovie = "Bob",
//                                    imageUrl = null
//                                ),
//                                CastMember(
//                                    realName = "Charlie White",
//                                    nameInMovie = "Charlie",
//                                    imageUrl = null
//                                ),
//
//                                )
//                        )
//                    }
//                    item {
//                        StarCastSection(
//                            title = "Star Cast",
//                            cast = detail.cast.take(5).map { cast ->
//                                CastMember(
//                                    realName = cast.name,
//                                    nameInMovie = cast.character ?: "Unknown",
//                                    imageUrl = cast.profilePath?.let { "https://image.tmdb.org/t/p/w500$it" }
//
//                                )
//                            },
//                            onSeeMoreClick = {},
//                            castContent = { }
//                        )
//                    }
//                }
                    item {
                        StaffInfoSection(
                            staffInfo = listOf(
                                "Director" to "John Doe",
                                "Director, Screenplay, Story" to "Christopher Nolan",
                                "Producer" to "Jane Smith",
                                "Writer" to "Alice Johnson"
                            ),
                            modifier = Modifier.padding(
                                top = 24.dp,
                                start = 16.dp,
                                end = 16.dp
                            )
                        )
                    }

                    item {
                        MovieListSection(
                            title = "You Might Also Like",
                            movies = listOf(
                                Movie(
                                    id = 1,
                                    title = "The Crimson Man",
                                    posterUrl = "",
                                    rating = 8.8f,
                                    genres = listOf("Action", "Sci-Fi"),
                                    duration = "2h 28m",
                                    releaseDate = "2010-07-16"
                                ),
                                Movie(
                                    id = 2,
                                    title = "Interstellar",
                                    posterUrl = "",
                                    rating = 9.9f,
                                    genres = listOf("Adventure", "Drama", "Sci-Fi"),
                                    duration = "2h 49m",
                                    releaseDate = "2014-11-07"
                                ),
                                Movie(
                                    id = 4,
                                    title = "PK",
                                    posterUrl = "",
                                    rating = 8.8f,
                                    genres = listOf("Action", "Sci-Fi"),
                                    duration = "2h 28m",
                                    releaseDate = "2010-07-16"
                                ),
                            ),
                            onClickShowMore = { },
                            onClickPoster = { },
                            modifier = Modifier.padding(top = 16.dp),
                            movieCardContent = { movie, modifier, onClick ->
                            }
                        )
                    }
                    item {
                        RatingSection(
                            icon = R.drawable.due_tone_star,
                            title = stringResource(com.moscow.cinverse.presentation.R.string.give_it_stars),
                            caption = stringResource(com.moscow.cinverse.presentation.R.string.let_the_world_know_how_you_felt),
                            onClickArrow = {},
                            modifier = Modifier.padding(
                                start = 16.dp,
                                end = 16.dp,
                                top = 24.dp
                            )
                        )
                    }

                    item {

                        SectionTitle(
                            title = stringResource(com.moscow.cinverse.presentation.R.string.top_reviews),
                            onClick = {},
                            modifier = Modifier.padding(
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 12.dp,
                                top = 24.dp
                            )
                        )
                        repeat(uiState.reviewsFlow?.size ?: 0) {
                            MovieReviewCard(
                                uiState.reviewsFlow?.get(it)?.name ?: "",
                                uiState.reviewsFlow?.get(it)?.username ?: "",
                                uiState.reviewsFlow?.get(it)?.reviewContent ?: "",
                                uiState.reviewsFlow?.get(it)?.rate ?: 0,
                                uiState.reviewsFlow?.get(it)?.date ?: "",
                                painterResource(R.drawable.outline_user),
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

        }
    }
}


@Preview(showBackground = true)
@Composable
private fun MovieDetailsScreenPreview() {
    val fakeState = MovieScreenState(
        movieDetailsUi = MovieScreenState.MovieDetailsUi(
            id = 1,
            title = "Inception",
            posterPath = "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
            rating = 8.8,
            genres = listOf("Action", "Sci-Fi", "Thriller"),
            releaseDate = "2010-07-16",
            duration = "2h 28m",
            description = "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a CEO."
        ),
        reviewsFlow = listOf(
            MovieScreenState.ReviewUi(
                id = 1,
                name = "Zeinab Wahdan",
                username = "zeinab979",
                rate = 5,
                reviewContent = "Amazing plot and stunning visuals. Nolan never disappoints!",
                date = "2025-07-17",
                userImage = "https://example.com/avatar1.jpg"
            ),
            MovieScreenState.ReviewUi(
                id = 2,
                name = "Ali Mostafa",
                username = "ali_dev",
                rate = 4,
                reviewContent = "Brilliant movie with a complex narrative. Highly recommended!",
                date = "2025-07-16",
                userImage = "https://example.com/avatar2.jpg"
            )
        ),
        isLoading = false,
        error = null,
        isReviewEmpty = false,
        shouldShowLoading = false,
        shouldShowError = false,
        errorMessage = ""
    )

    CineVerseTheme {
        MovieDetailsContent(uiState = fakeState)
    }
}
