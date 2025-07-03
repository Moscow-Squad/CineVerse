package com.moscow.cineverse.designSystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun InfoSection(
    title: String,
    genres: List<String> = emptyList(),
    description: String? = null,
    modifier: Modifier = Modifier,
    paddingBetween: Dp = 2.dp,
    showGenres: Boolean = true,
    maxDescriptionLines: Int = 3,
    titleTextAlign: TextAlign = TextAlign.Start,
    descriptionTextAlign: TextAlign = TextAlign.Start
) {
    Column(modifier = modifier) {
        if (title.isNotEmpty()) {
            Text(
                text = title,
                color = Theme.colors.shade.primary,
                style = Theme.textStyle.body.medium.medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = titleTextAlign,
                modifier = Modifier.fillMaxWidth()
            )
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
                genres.forEachIndexed { index, genre ->
                    Text(
                        text = if (index < genres.lastIndex) "$genre," else genre,
                        style = Theme.textStyle.body.small.regular,
                        color = Theme.colors.shade.secondary,
                        overflow = TextOverflow.Ellipsis
                    )
                }
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
                genres = listOf("Action", "Crime", "Drama")
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
                showGenres = false
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
                description = "A team of explorers travel through a wormhole in space."
            )
        }
    }
}