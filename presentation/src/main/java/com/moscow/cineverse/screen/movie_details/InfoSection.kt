package com.moscow.cineverse.screen.movie_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.design_system.R

@Composable
fun InfoSection(
    title: String,
    modifier: Modifier = Modifier,
    genres: List<String> = emptyList(),
    description: String? = null,
    rating: Float? = null,
    paddingTop: Dp = 2.dp,
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
                            .size(22.dp)
                            .padding(start = 4.dp)
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
                    .padding(top = paddingTop)
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
                    .padding(top = paddingTop)
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
