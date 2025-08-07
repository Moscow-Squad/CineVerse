package com.moscow.cineverse.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.blur.OnBlurContent
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.design_system.R
import com.moscow.cineverse.image_viewer.component.SafeImageViewer
import com.moscow.cineverse.screen.movie_details.InfoSection
import com.moscow.cineverse.utlis.ViewMode

@Composable
fun <T : Any> MovieCard(
    modifier: Modifier = Modifier,
    movieData: T,
    viewMode: ViewMode = ViewMode.GRID,
    showRating: Boolean = true,
    onMovieClick: (Int) -> Unit = {},
    titleTextAlign: TextAlign = TextAlign.Start,
    showTitle: Boolean = true,
    showBackdrop: Boolean = false,
    getId: (T) -> Int,
    getTitle: (T) -> String,
    getPosterUrl: (T) -> String,
    getBackdropUrl: (T) -> String = { "" },
    getRating: (T) -> Float,
    getGenres: (T) -> List<String>,
    getDuration: (T) -> String,
    getReleaseDate: (T) -> String,
) {
    when (viewMode) {
        ViewMode.GRID -> GridMovieCard(
            movieData = movieData,
            showRating = showRating,
            onMovieClick = onMovieClick,
            modifier = modifier,
            titleTextAlign = titleTextAlign,
            showTitle = showTitle,
            showBackdrop = showBackdrop,
            getId = getId,
            getTitle = getTitle,
            getPosterUrl = getPosterUrl,
            getBackdropUrl = getBackdropUrl,
            getRating = getRating,
        )

        ViewMode.LIST -> ListMovieCard(
            movieData = movieData,
            onMovieClick = onMovieClick,
            modifier = modifier,
            getId = getId,
            getTitle = getTitle,
            getPosterUrl = getPosterUrl,
            getRating = getRating,
            getGenres = getGenres,
            getDuration = getDuration,
            getReleaseDate = getReleaseDate
        )
    }
}

@Composable
private fun RemoteImagePlaceholder(
    modifier: Modifier = Modifier,
    cardColor: Color = Theme.colors.brand.tertiary,
    iconSize: Dp = 24.dp
) {
    Box(
        modifier = modifier
            .background(
                cardColor,
                RoundedCornerShape(
                    topStart = Theme.radius.large,
                    topEnd = Theme.radius.large,
                    bottomStart = Theme.radius.large
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.due_tone_image),
            contentDescription = "Movie Poster",
            tint = Theme.colors.shade.secondary,
            modifier = Modifier.size(iconSize)
        )
    }
}

@Composable
private fun <T> GridMovieCard(
    modifier: Modifier = Modifier,
    movieData: T,
    showTitle: Boolean = true,
    showRating: Boolean = true,
    showBackdrop: Boolean = false,
    onMovieClick: (Int) -> Unit,
    titleTextAlign: TextAlign,
    getId: (T) -> Int,
    getTitle: (T) -> String,
    getPosterUrl: (T) -> String,
    getBackdropUrl: (T) -> String = { "" },
    getRating: (T) -> Float,
) {
    Column(
        modifier = modifier
    ) {
        Card(
            modifier = Modifier
                .height(180.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(Theme.radius.large)),
            shape = RoundedCornerShape(Theme.radius.large)
        ) {
            Box {
                val posterUrl =
                    if (showBackdrop) getBackdropUrl(movieData) else getPosterUrl(movieData)

                SafeImageViewer(
                    imageUrl = posterUrl,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(Theme.radius.large))
                        .clickable { onMovieClick(getId(movieData)) },
                    placeholderContent = {
                        RemoteImagePlaceholder(
                            modifier = Modifier.fillMaxSize(),
                            cardColor = Theme.colors.background.card,
                            iconSize = 32.dp
                        )
                    },
                    errorContent = {
                        RemoteImagePlaceholder(
                            modifier = Modifier.fillMaxSize(),
                            cardColor = Theme.colors.background.card,
                            iconSize = 32.dp
                        )
                    }
                ) {
                    OnBlurContent(
                        hintText = stringResource(com.moscow.cinverse.presentation.R.string.sensitive_content),
                    )
                }

                val rating = getRating(movieData)
                if (showRating && rating >= 0) {
                    Surface(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp),
                        shape = CircleShape,
                        color = Theme.colors.background.card
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "%.1f".format(rating),
                                color = Theme.colors.shade.primary,
                                style = Theme.textStyle.label.medium.medium
                            )
                            Icon(
                                painter = painterResource(R.drawable.due_tone_star),
                                contentDescription = "Rating",
                                tint = Theme.colors.additional.primary.yellow,
                                modifier = Modifier
                                    .size(23.dp)
                                    .padding(start = 4.dp)
                            )
                        }
                    }
                }
            }
        }

        val title = getTitle(movieData)
        if (title.isNotEmpty() && showTitle) {
            Text(
                text = title,
                color = Theme.colors.shade.secondary,
                style = Theme.textStyle.body.medium.medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = titleTextAlign,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = { onMovieClick(getId(movieData)) }
                    )
            )
        }
    }
}

@Composable
private fun <T> ListMovieCard(
    movieData: T,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    getId: (T) -> Int,
    getTitle: (T) -> String,
    getPosterUrl: (T) -> String,
    getRating: (T) -> Float,
    getGenres: (T) -> List<String>,
    getDuration: (T) -> String,
    getReleaseDate: (T) -> String
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(95.dp)
            .clip(RoundedCornerShape(Theme.radius.large))
            .clickable { onMovieClick(getId(movieData)) }
           ,
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(Theme.radius.large),
        colors = CardDefaults.cardColors(containerColor = Theme.colors.background.card)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxSize()) {
                val posterUrl = getPosterUrl(movieData)

                SafeImageViewer(
                    imageUrl = posterUrl,
                    modifier = Modifier
                        .width(64.dp)
                        .fillMaxHeight()
                        .clip(
                            RoundedCornerShape(
                                topStart = Theme.radius.large,
                                topEnd = Theme.radius.large,
                                bottomStart = Theme.radius.large
                            )
                        )
                        ,
                    placeholderContent = {
                        RemoteImagePlaceholder(
                            modifier = Modifier
                                .width(64.dp)
                                .fillMaxHeight(),
                            cardColor = Theme.colors.brand.tertiary,
                            iconSize = 24.dp
                        )
                    },
                    errorContent = {
                        RemoteImagePlaceholder(
                            modifier = Modifier
                                .width(64.dp)
                                .fillMaxHeight(),
                            cardColor = Theme.colors.brand.tertiary,
                            iconSize = 24.dp
                        )
                    }
                ) {
                    OnBlurContent(
                        hintText = "",
                        isAddedText = false
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 12.5.dp, horizontal = 12.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    InfoSection(
                        title = getTitle(movieData),
                        genres = getGenres(movieData),
                        paddingBetween = 4.dp,
                        rating = getRating(movieData)
                    )

                    DurationAndDateSection(
                        duration = getDuration(movieData),
                        releaseDate = getReleaseDate(movieData)
                    )
                }
            }
        }
    }
}

data class MockMovieData(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val rating: Float,
    val genres: List<String>,
    val duration: String,
    val releaseDate: String
)

@Composable
fun DurationAndDateSection(
    modifier: Modifier = Modifier,
    duration: String,
    releaseDate: String
) {
    Row(
        modifier = modifier.padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (duration.isNotEmpty()) {
            Icon(
                painter = painterResource(R.drawable.due_tone_clock),
                contentDescription = "Duration",
                tint = Theme.colors.shade.secondary,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = duration,
                style = Theme.textStyle.label.medium.medium,
                color = Theme.colors.shade.secondary,
                modifier = Modifier.padding(start = 4.dp)
            )
        }

        if (duration.isNotEmpty() && releaseDate.isNotEmpty()) {
            Spacer(modifier = Modifier.width(8.dp))
        }

        if (releaseDate.isNotEmpty()) {
            Icon(
                painter = painterResource(R.drawable.due_tone_calendar),
                contentDescription = "Release Date",
                tint = Theme.colors.shade.secondary,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = releaseDate,
                style = Theme.textStyle.label.medium.medium,
                color = Theme.colors.shade.secondary,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true, name = "Grid View - Safe Image")
@Composable
fun GridMovieCardSafeImagePreview() {
    MaterialTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            MovieCard(
                movieData = MockMovieData(
                    id = 1,
                    title = "The Dark Knight",
                    posterUrl = "https://example.com/poster.jpg",
                    rating = 9.0f,
                    genres = listOf("Action", "Crime", "Drama"),
                    duration = "2h 32m",
                    releaseDate = "2008, Jul 18"
                ),
                viewMode = ViewMode.GRID,
                onMovieClick = {},
                getId = { it.id },
                getTitle = { it.title },
                getPosterUrl = { it.posterUrl },
                getRating = { it.rating },
                getGenres = { it.genres },
                getDuration = { it.duration },
                getReleaseDate = { it.releaseDate }
            )
        }
    }
}

@Preview(showBackground = true, name = "List View - Safe Image")
@Composable
fun ListMovieCardSafeImagePreview() {
    CineVerseTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            MovieCard(
                movieData = MockMovieData(
                    id = 1,
                    title = "Inception",
                    posterUrl = "https://example.com/poster.jpg",
                    rating = 8.8f,
                    genres = listOf("Action", "Sci-Fi", "Thriller"),
                    duration = "2h 28m",
                    releaseDate = "2008, Jul 18"
                ),
                viewMode = ViewMode.LIST,
                onMovieClick = {},
                getId = { it.id },
                getTitle = { it.title },
                getPosterUrl = { it.posterUrl },
                getRating = { it.rating },
                getGenres = { it.genres },
                getDuration = { it.duration },
                getReleaseDate = { it.releaseDate }
            )
        }
    }
}