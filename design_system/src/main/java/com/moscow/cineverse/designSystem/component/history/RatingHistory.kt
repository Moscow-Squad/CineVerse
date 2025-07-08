package com.moscow.cineverse.designSystem.component.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.Movie
import com.moscow.cineverse.designSystem.component.MoviePosterCard
import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme
import kotlinx.datetime.LocalDateTime

data class UserRating(
    val movie: Movie,
    val rating: Int,
    val date: LocalDateTime
)

@Composable
fun RatingHistory(
    modifier: Modifier = Modifier,
    rating: UserRating
) {
    Column {
    Row(modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically) {
        Text(
            "You gave it",
            style = Theme.textStyle.body.small.medium,
            color = Theme.colors.shade.primary
        )

        Row(
            modifier = Modifier.padding(start = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(5) { index ->
                Icon(
                    painter = if (index < rating.rating)
                        painterResource(id = Theme.icons.bold.star)
                    else painterResource(id = Theme.icons.outline.star),
                    contentDescription = null,
                    tint = if (index < rating.rating)
                        Theme.colors.additional.primary.yellow
                    else Theme.colors.shade.tertiary,
                    modifier = Modifier.size(12.dp)
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = rating.date.formatPretty(),
            style = Theme.textStyle.body.small.regular,
            color = Theme.colors.shade.secondary
        )
    }
        MoviePosterCard(
            modifier = Modifier.padding(top = 12.dp),
            movie = rating.movie,
            viewMode = ViewMode.LIST
        )
    }
}

private fun LocalDateTime.formatPretty(): String {
    val monthAbbreviation = month.name.lowercase()
        .replaceFirstChar { it.uppercase() }
        .take(3)
    return "on $monthAbbreviation $dayOfMonth, $year"
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewRatingHistory() {
    val sampleMovie = Movie(
        id = 1,
        title = "Inception",
        posterUrl = "https://example.com/inception.jpg",
        rating = 8.8f,
        genres = listOf("Action", "Sci-Fi"),
        duration = "2h 28m",
        releaseDate = "2010-07-16"
    )

    val sampleUserRating = UserRating(
        movie = sampleMovie,
        rating = 3,
        date = LocalDateTime(2024, 12, 15, 14, 30)
    )

    CineVerseTheme{
        RatingHistory(
            rating = sampleUserRating
        )
    }
}