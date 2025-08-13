package com.moscow.cineverse.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.button.MovieButton
import com.moscow.cineverse.designSystem.component.wrapper.MovieIcon
import com.moscow.cineverse.designSystem.component.wrapper.MovieText
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cinverse.presentation.R

@Composable
fun EmptyCollection(
    onStartCollectingClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth(.7f)
    ) {
        MovieIcon(
            painter = painterResource(R.drawable.due_tone_video_library),
            tint = Theme.colors.brand.primary,
            modifier = Modifier
                .clip(CircleShape)
                .background(
                    color = Theme.colors.button.disabled,
                    shape = CircleShape
                )
                .padding(18.dp)
        )
        MovieText(
            text = stringResource(R.string.no_collections_yet),
            color = Theme.colors.shade.primary,
            style = Theme.textStyle.title.small,
            modifier = Modifier.padding(top = 16.dp)
        )
        MovieText(
            text = stringResource(R.string.no_collections_yet_description),
            color = Theme.colors.shade.primary,
            style = Theme.textStyle.body.small.medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )

        MovieButton(
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth(),
            cornerRadius = Theme.radius.medium,
            buttonColor = Theme.colors.button.primary,
            onClick = onStartCollectingClick,
            buttonText = stringResource(R.string.start_collecting),
            textColor = Theme.colors.button.onPrimary,
            textStyle = Theme.textStyle.body.small.medium,
        )

    }
}