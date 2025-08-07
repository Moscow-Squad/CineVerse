package com.moscow.cineverse.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cinverse.presentation.R


@Composable
fun StorylineSection(
    description: String?,
    modifier: Modifier = Modifier
) {
    if (!description.isNullOrEmpty()) {
        Text(
            text = stringResource(R.string.storyline),
            style = Theme.textStyle.title.small,
            color = Theme.colors.shade.primary,
            modifier = modifier.padding(16.dp, top = 24.dp, bottom = 8.dp),
        )
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            overflow = TextOverflow.Ellipsis,
            style = Theme.textStyle.body.small.semiBold,
            color = Theme.colors.shade.secondary,
            text = description,
            textAlign = TextAlign.Justify
        )
    }
}