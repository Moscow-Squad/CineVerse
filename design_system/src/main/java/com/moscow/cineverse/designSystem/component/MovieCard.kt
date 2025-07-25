package com.moscow.cineverse.designSystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.design_system.R
import com.example.image_viewer.component.SafeImageViewer
import com.moscow.cineverse.designSystem.component.blur.OnBlurContent
import com.moscow.cineverse.designSystem.component.blur.RemoteImagePlaceholder
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun <T : Any> MovieCard(
    modifier: Modifier = Modifier,
    movieData: T,
    viewMode: ViewMode = ViewMode.GRID,
    showRating: Boolean = true,
    onMovieClick: (Int) -> Unit = {},
    titleTextAlign: TextAlign = TextAlign.Start,
    showTitle: Boolean = true,
    getId: (T) -> Int,
    getTitle: (T) -> String,
    getPosterUrl: (T) -> String,
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
            getId = getId,
            getTitle = getTitle,
            getPosterUrl = getPosterUrl,
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
private fun PlaceholderCard(
    modifier: Modifier = Modifier,
    cardColor: Color = Theme.colors.brand.tertiary,
    iconSize: Dp = 24.dp
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                cardColor, RoundedCornerShape(
                    topStart = Theme.radius.large,
                    topEnd = Theme.radius.large,
                    bottomStart = Theme.radius.large
                )
            ), contentAlignment = Alignment.Center
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
    onMovieClick: (Int) -> Unit,
    titleTextAlign: TextAlign,
    getId: (T) -> Int,
    getTitle: (T) -> String,
    getPosterUrl: (T) -> String,
    getRating: (T) -> Float,
) {
    Column(
        modifier = modifier
    ) {
        Card(
            modifier = Modifier
                .height(208.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(Theme.radius.large))
                .clickable { onMovieClick(getId(movieData)) },
            shape = RoundedCornerShape(Theme.radius.large)
        ) {
            Box {
                val posterUrl = getPosterUrl(movieData)

                LoadImageWithPlaceholder(
                    posterUrl = posterUrl,
                    contentDescription = getTitle(movieData),
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(Theme.radius.large)),
                    placeholderModifier = Modifier.fillMaxSize(),
                    placeholderCardColor = Theme.colors.background.card,
                    placeholderIconSize = 32.dp
                )

                val rating = getRating(movieData)
                if (showRating && rating > 0) {
                    RatingDisplaySection(
                        rating = rating,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                    )
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
            .height(88.dp)
            .clip(RoundedCornerShape(Theme.radius.large))
            .clickable { onMovieClick(getId(movieData)) },
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(Theme.radius.large),
        colors = CardDefaults.cardColors(containerColor = Theme.colors.background.card)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            Row(modifier = Modifier.fillMaxSize()) {

                val posterUrl = getPosterUrl(movieData)

                LoadImageWithPlaceholder(
                    posterUrl = posterUrl,
                    contentDescription = getTitle(movieData),
                    modifier = Modifier
                        .width(64.dp)
                        .fillMaxHeight()
                        .clip(
                            RoundedCornerShape(
                                topStart = Theme.radius.large,
                                topEnd = Theme.radius.large,
                                bottomStart = Theme.radius.large
                            )
                        ),
                    placeholderModifier = Modifier
                        .width(64.dp)
                        .fillMaxHeight(),
                    placeholderCardColor = Theme.colors.brand.tertiary,
                    placeholderIconSize = 24.dp
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 12.5.dp, horizontal = 12.dp),
                ) {
                    InfoSection(
                        title = getTitle(movieData),
                        genres = getGenres(movieData),
                        paddingBetween = 4.dp,
                        rating = getRating(movieData)
                    )

                    DurationAndDateSection(
                        duration = getDuration(movieData), releaseDate = getReleaseDate(movieData)
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
fun LoadImageWithPlaceholder(
    posterUrl: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    placeholderModifier: Modifier = Modifier,
    placeholderCardColor: Color,
    placeholderIconSize: Dp
) {
    if (posterUrl.isNotEmpty()) {
        SafeImageViewer(
            imageUrl = posterUrl,
            modifier = modifier.fillMaxWidth(),
            placeholderContent = {
                RemoteImagePlaceholder(Modifier.fillMaxSize())
            },
            errorContent = {
                RemoteImagePlaceholder(Modifier.fillMaxSize())
            },
        ) {
            OnBlurContent(
                hintText = stringResource(R.string.unsuitable_image),
                textStyle = Theme.textStyle.body.small.regular.copy(
                    color = Color(0x99FFFFFF)
                ),
                iconSize = 24.dp,
                icon = painterResource(R.drawable.icon_eye_slash),
            )
        }
    }
}

@Composable
fun DurationAndDateSection(
    modifier: Modifier = Modifier, duration: String, releaseDate: String
) {
    Row(
        modifier = modifier.padding(top = 8.dp), verticalAlignment = Alignment.CenterVertically
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

@Preview(showBackground = true, name = "Grid View - Loading State")
@Composable
fun GridMovieCardLoadingPreview() {
    MaterialTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            MovieCard(
                movieData = MockMovieData(
                id = 1,
                title = "The Dark Knight",
                posterUrl = "",
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
                getReleaseDate = { it.releaseDate })
        }
    }
}

@Preview(showBackground = true, name = "List View - Loading State")
@Composable
fun ListMovieCardLoadingPreview() {
    CineVerseTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            MovieCard(
                movieData = MockMovieData(
                id = 1,
                title = "Inception",
                posterUrl = "",
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
                getReleaseDate = { it.releaseDate })
        }
    }
}