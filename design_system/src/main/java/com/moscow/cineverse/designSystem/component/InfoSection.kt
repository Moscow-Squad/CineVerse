package com.moscow.cineverse.designSystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.design_system.R
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun InfoSection(
    title: String,
    modifier: Modifier = Modifier,
    genres: List<String> = emptyList(),
    description: String? = null,
    rating: Float? = null,
    paddingBetween: Dp = 2.dp,
    showGenres: Boolean = true,
    showTitle: Boolean = true,
    showRating: Boolean = true,
    maxDescriptionLines: Int = 3,
    titleTextAlign: TextAlign = TextAlign.Start,
    descriptionTextAlign: TextAlign = TextAlign.Start
) {
    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (title.isNotEmpty() && showTitle) {
                Text(
                    text = title,
                    color = Theme.colors.shade.primary,
                    style = Theme.textStyle.body.medium.medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = titleTextAlign,
                    modifier = Modifier.weight(1f)
                )
            }
            if (rating != null && rating > 0 && showRating) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "%.1f".format(rating),
                        color = Theme.colors.shade.primary,
                        style = Theme.textStyle.label.medium.medium,
                        fontSize = 12.sp
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

        if (!description.isNullOrEmpty()) {
            Text(
                text = description,
                style = Theme.textStyle.body.small.regular,
                color = Theme.colors.shade.secondary,
                modifier = Modifier
                    .padding(top = paddingBetween)
                    .fillMaxWidth(),
                maxLines = maxDescriptionLines,
                overflow = TextOverflow.Ellipsis,
                textAlign = descriptionTextAlign
            )
        } else if (showGenres && genres.isNotEmpty()) {
            Row(
                horizontalArrangement = if (descriptionTextAlign == TextAlign.Center) {
                    Arrangement.Center
                } else if (descriptionTextAlign == TextAlign.End) {
                    Arrangement.End
                } else {
                    Arrangement.spacedBy(4.dp)
                },
                modifier = Modifier
                    .padding(top = paddingBetween)
                    .fillMaxWidth()
            ) {
                var genresText = genres.joinToString(", ")
                Text(
                    text = genresText,
                    style = Theme.textStyle.body.small.regular,
                    color = Theme.colors.shade.secondary,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Movie Info - Full")
@Composable
fun MovieInfoPreview() {
    CineVerseTheme{
        Column(modifier = Modifier.padding(16.dp)) {
            InfoSection(
                title = "The Dark Knight",
                genres = listOf("Action", "Crime", "Drama"),
                rating = 4f
            )
        }
    }
}

@Preview(showBackground = true, name = "Movie Info - Title Only")
@Composable
fun MovieInfoTitleOnlyPreview() {
    MaterialTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            InfoSection(
                title = "Inception",
                genres = listOf("Action", "Sci-Fi", "Thriller"),
                showGenres = false,
                rating = 4f
            )
        }
    }
}

@Preview(showBackground = true, name = "Movie Info - Custom Styling")
@Composable
fun MovieInfoCustomPreview() {
    MaterialTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            InfoSection(
                title = "Interstellar",
                description = "A team of explorers travel through a wormhole in space.",
                rating = 4f
            )
        }
    }
}

@Preview(showBackground = true, name = "Movie Info - No Rating")
@Composable
fun MovieInfoNoRatingPreview() {
    MaterialTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            InfoSection(
                title = "Unknown Movie",
                genres = listOf("Mystery", "Thriller"),
                showRating = false // No rating provided
            )
        }
    }
}