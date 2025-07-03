package com.moscow.cineverse.designSystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.design_system.R
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme

data class Movie(
    val id: Int = 0,
    val title: String = "",
    val posterUrl: String = "",
    val rating: Float = 0f,
    val genres: List<String> = emptyList(),
    val duration: String = "",
    val releaseDate: String = ""
)

@Composable
fun MoviePosterCard(
    modifier: Modifier = Modifier,
    movie: Movie = Movie(),
    viewMode: ViewMode = ViewMode.GRID,
    showRating: Boolean = true,
    onMovieClick: (Movie) -> Unit = {},
    infoModifier: Modifier = Modifier,
    titleTextAlign : TextAlign = TextAlign.Start,
    descriptionTextAlign : TextAlign = TextAlign.Start,
    showGenres: Boolean = false
) {
    when (viewMode) {
        ViewMode.GRID -> GridMovieCard(
            movie = movie,
            showRating = showRating,
            onMovieClick = onMovieClick,
            modifier = modifier,
            infoModifier = infoModifier,
            titleTextAlign = titleTextAlign,
            descriptionTextAlign = descriptionTextAlign,
            showGenres = showGenres
        )

        ViewMode.LIST -> ListMovieCard(
            movie = movie,
            onMovieClick = onMovieClick,
            modifier = modifier
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
private fun GridMovieCard(
    modifier: Modifier = Modifier,
    movie: Movie,
    showGenres: Boolean = false,
    showRating: Boolean = true,
    onMovieClick: (Movie) -> Unit,
    infoModifier: Modifier = Modifier,
    titleTextAlign : TextAlign,
    descriptionTextAlign : TextAlign
) {
    Column {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(182.dp)
                .clickable { onMovieClick(movie) },
            shape = RoundedCornerShape(Theme.radius.large)
        ) {
            Box {
                if (movie.posterUrl.isNotEmpty()) {
                    AsyncImage(
                        model = movie.posterUrl,
                        contentDescription = movie.title,
                        placeholder = painterResource(id = R.drawable.due_tone_image),
                        error = painterResource(id = R.drawable.due_tone_image),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(Theme.radius.large))
                    )
                } else {
                    PlaceholderCard(
                        modifier = Modifier.fillMaxSize(),
                        cardColor = Theme.colors.background.card,
                        iconSize = 32.dp
                    )
                }

                if (showRating && movie.rating > 0) {
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
                                text = String.format("%.1f", movie.rating),
                                color = Theme.colors.shade.primary,
                                style = Theme.textStyle.label.medium.medium
                            )
                            Icon(
                                painter = painterResource(R.drawable.due_tone_star),
                                contentDescription = "Rating",
                                tint = Theme.colors.additional.primary.yellow,
                                modifier = Modifier
                                    .size(16.dp)
                                    .padding(start = 4.dp)
                            )
                        }
                    }
                }
            }
        }

        InfoSection(
            title = movie.title,
            genres = movie.genres,
            showGenres = showGenres,
            modifier = infoModifier.padding(top = 8.dp),
            titleTextAlign = titleTextAlign,
            descriptionTextAlign = descriptionTextAlign
        )
    }
}

@Composable
private fun ListMovieCard(
    movie: Movie,
    onMovieClick: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(88.dp)
            .clickable { onMovieClick(movie) },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(Theme.radius.large),
        colors = CardDefaults.cardColors(containerColor = Theme.colors.background.card)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            Row(modifier = Modifier.fillMaxSize()) {

                if (movie.posterUrl.isNotEmpty()) {
                    AsyncImage(
                        model = movie.posterUrl,
                        contentDescription = movie.title,
                        placeholder = painterResource(id = R.drawable.due_tone_image),
                        error = painterResource(id = R.drawable.due_tone_image),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(64.dp)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(Theme.radius.small))
                    )
                } else {
                    PlaceholderCard(
                        modifier = Modifier
                            .width(64.dp)
                            .fillMaxHeight(),
                        cardColor = Theme.colors.brand.tertiary,
                        iconSize = 24.dp
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 12.dp, horizontal = 12.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    InfoSection(
                        title = movie.title,
                        genres = movie.genres,
                        paddingBetween = 4.dp
                    )

                    Row(
                        modifier = Modifier.padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (movie.duration.isNotEmpty()) {
                            Icon(
                                painter = painterResource(R.drawable.due_tone_clock),
                                contentDescription = "Duration",
                                tint = Theme.colors.shade.secondary,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = movie.duration,
                                style = Theme.textStyle.label.medium.medium,
                                color = Theme.colors.shade.secondary,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }

                        if (movie.duration.isNotEmpty() && movie.releaseDate.isNotEmpty()) {
                            Spacer(modifier = Modifier.width(8.dp))
                        }

                        if (movie.releaseDate.isNotEmpty()) {
                            Icon(
                                painter = painterResource(R.drawable.due_tone_calendar),
                                contentDescription = "Release Date",
                                tint = Theme.colors.shade.secondary,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = movie.releaseDate,
                                style = Theme.textStyle.label.medium.medium,
                                color = Theme.colors.shade.secondary,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    }
                }
            }

            if (movie.rating > 0) {
                Row(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(6.dp)
                        .padding(horizontal = 6.dp, vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = String.format("%.1f", movie.rating),
                        color = Theme.colors.shade.primary,
                        style = Theme.textStyle.label.medium.medium
                    )
                    Icon(
                        painter = painterResource(R.drawable.due_tone_star),
                        contentDescription = "Rating",
                        tint = Theme.colors.additional.primary.yellow,
                        modifier = Modifier
                            .size(16.dp)
                            .padding(start = 2.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Grid View - Loading State")
@Composable
fun GridMovieCardLoadingPreview() {
    MaterialTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            MoviePosterCard(
                movie = Movie(
                    id = 1,
                    title = "The Dark Knight",
                    posterUrl = "",
                    rating = 9.0f,
                    genres = listOf("Action", "Crime", "Drama"),
                    duration = "2h 32m",
                    releaseDate = "2008, Jul 18"
                ),
                viewMode = ViewMode.GRID,
                onMovieClick = {}
            )
        }
    }
}

@Preview(showBackground = true, name = "List View - Loading State")
@Composable
fun ListMovieCardLoadingPreview() {
    CineVerseTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            MoviePosterCard(
                movie = Movie(
                    id = 1,
                    title = "Inception",
                    posterUrl = "",
                    rating = 8.8f,
                    genres = listOf("Action", "Sci-Fi", "Thriller"),
                    duration = "2h 28m",
                    releaseDate = "2008, Jul 18"
                ),
                viewMode = ViewMode.LIST,
                onMovieClick = {}
            )
        }
    }
}