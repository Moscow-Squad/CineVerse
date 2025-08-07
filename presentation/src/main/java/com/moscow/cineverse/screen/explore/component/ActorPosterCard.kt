package com.moscow.cineverse.screen.explore.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.blur.OnBlurContent
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.image_viewer.component.SafeImageViewer
import com.moscow.cineverse.screen.explore.ExploreScreenState

@Composable
fun ActorPosterCard(
    actor: ExploreScreenState.ActorUiState,
    enableBlur: Boolean,
    modifier: Modifier = Modifier,
    titleTextAlign: TextAlign = TextAlign.Center,
    showTitle: Boolean = true,
    onActorClicked: (Int) -> Unit = {}
) {
    Column(
        modifier = modifier.clickable { onActorClicked(actor.id) }
    ) {
        Card(
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(Theme.radius.large)),
            shape = RoundedCornerShape(Theme.radius.large)
        ) {
            Box {
                val posterUrl = actor.profilePath

                SafeImageViewer(
                    imageUrl = posterUrl,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(Theme.radius.large)),
                    isBlurEnabled = enableBlur,
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
                        hintText = stringResource(com.moscow.cinverse.presentation.R.string.sensitive_content)
                    )
                }
            }
        }

        val title = actor.title
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
                    .clickable { onActorClicked(actor.id) }
            )
        }
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
            painter = painterResource(com.moscow.cineverse.design_system.R.drawable.due_tone_image),
            contentDescription = "Movie Poster",
            tint = Theme.colors.shade.secondary,
            modifier = Modifier.size(iconSize)
        )
    }
}