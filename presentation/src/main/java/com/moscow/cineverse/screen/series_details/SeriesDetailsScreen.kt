/*
package com.moscow.cineverse.screen.series_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.design_system.R
import com.moscow.cineverse.designSystem.component.Movie
import com.moscow.cineverse.designSystem.component.MovieAppBar
import com.moscow.cineverse.designSystem.component.MovieListSection
import com.moscow.cineverse.designSystem.component.SectionTitle
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.CastMember
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.MovieCardDetails
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.MovieReviewCard
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.RatingSection
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.Season
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.SeasonCard
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.StaffInfoSection
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.StarCastSection
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun SeriesDetailsScreen(
    seriesId: Int,
    viewModel: SeriesDetailsViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(seriesId) {
        viewModel.loadSeriesDetails(seriesId)
        viewModel.loadReviews(seriesId, page = 1)
    }

    val detail = uiState.seriesDetail
    val reviews = uiState.reviews
    val isLoading = uiState.isLoading
    val error = uiState.error

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colors.background.screen)
    ) {
        if (isLoading && detail == null) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Theme.colors.brand.primary
            )
        } else {
            val textColor = Theme.colors.shade.secondary
            LazyColumn(
                modifier = Modifier.background(Theme.colors.background.screen)
            ) {
                item { MovieAppBar() }

                item {
                    MovieCardDetails(
                        posterUrl = detail?.posterPath?.let { "https://image.tmdb.org/t/p/w500$it" } ?: "",
                        title = detail?.title ?: "Loading...",
                        genres = detail?.genres?.joinToString(", ") { it.name } ?: "",
                        rating = detail?.rating?.toString() ?: "0.0",
                        duration = detail?.runtime ?: "N/A",
                        releaseDate = detail?.releaseDate?.let { formatDate(it) } ?: "",
                        type = detail?.type ?: "SERIES"
                    )
                }

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

                // Show season info if available
                if (detail?.numberOfSeasons != null && detail.numberOfSeasons!! > 0) {
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

                    item {
                        SeasonCard(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            season = Season(
                                seasonNumber = detail.numberOfSeasons!!,
                                episodeCount = detail.numberOfEpisodes ?: 0,
                                airDate = detail.releaseDate?.substring(0, 4) ?: "N/A",
                                posterUrl = detail.posterPath?.let { "https://image.tmdb.org/t/p/w500$it" } ?: "",
                                caption = "Season ${detail.numberOfSeasons} of the series",
                                rate = detail.rating.toFloat()
                            )
                        )
                    }
                }

                if (detail?.cast?.isNotEmpty() == true) {
                    item {
                       */
/* StarCastSection(
                            modifier = Modifier
                                .background(Theme.colors.background.screen)
                                .padding(10.dp),
                            seeMore = {},
                            cast = detail.cast.take(5).map { cast ->
                                CastMember(
                                    realName = cast.name,
                                    nameInMovie = cast.character ?: "Unknown",
                                    imageUrl = cast.profilePath?.let { "https://image.tmdb.org/t/p/w500$it" }
                                )
                            }
                        )
                    }*//*

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
                        title = "Give it Stars!",
                        caption = "Let the world know how you felt.",
                        onClickArrow = {},
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
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
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
        }
    }
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

@Preview
@Composable
private fun SeriesDetailsScreenPreview() {
    CineVerseTheme {
        SeriesDetailsScreen(seriesId = 1396) // Breaking Bad ID for preview
    }
}*/
