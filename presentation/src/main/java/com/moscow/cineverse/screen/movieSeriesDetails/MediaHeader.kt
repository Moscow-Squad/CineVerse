package com.moscow.cineverse.screen.movieSeriesDetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moscow.cineverse.designSystem.component.blur.OnBlurContent
import com.moscow.cineverse.designSystem.component.blur.RemoteImagePlaceholder
import com.moscow.cineverse.designSystem.component.button.MovieFloatingButton
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.image_viewer.component.SafeImageViewer
import com.moscow.cinverse.presentation.R

@Composable
fun MediaHeader(
    modifier: Modifier = Modifier,
    scrollState: LazyListState,
    title: String,
    genres: String,
    rating: String,
    duration: String,
    releaseDate: String,
    posterUrl: String,
    type: String,
    enableBlur: String,
    onSaveClick: () -> Unit = {},
    onPlayClick: () -> Unit = {},
    isSaveEnabled: Boolean = true
) {
    Box(
        modifier = Modifier,
    ) {
        val formattedRating = try {
            val ratingValue = rating.toDouble()
            String.format("%.1f", ratingValue)
        } catch (_: NumberFormatException) {
            "0.0"
        }

        val maxScroll = 400f
        val maxAchievedScroll = remember { mutableStateOf(0f) }
        val screenWidth = LocalConfiguration.current.screenWidthDp.dp

        val absoluteScroll = remember(scrollState) {
            derivedStateOf {
                scrollState.firstVisibleItemIndex * 1000 +
                        scrollState.firstVisibleItemScrollOffset
            }
        }
        val clampedScrollValue by remember(scrollState) {
            derivedStateOf {
                val current = absoluteScroll.value.toFloat()
                maxAchievedScroll.value = current
                maxAchievedScroll.value.coerceIn(0f, maxScroll)
            }
        }
        val imageStartPadding by remember {
            derivedStateOf {
                val start = (screenWidth - 216.dp - 16.dp) / 2
                val end = 0.dp
                start - (start - end) * (clampedScrollValue / maxScroll)
            }
        }
        val imageRadius by remember {
            derivedStateOf {
                val start = 16.dp
                val end = 100.dp
                start - (start - end) * (clampedScrollValue / maxScroll)
            }
        }

        val imageHeight by remember {
            derivedStateOf {
                val start = 289.dp
                val end = 40.dp
                start - (start - end) * (clampedScrollValue / maxScroll)
            }
        }
        val imageWidth by remember {
            derivedStateOf {
                val start = 216.dp
                val end = 40.dp
                start - (start - end) * (clampedScrollValue / maxScroll)
            }
        }

        val collectionEndPadding by remember {
            derivedStateOf {
                val start = 0.dp
                val end = 48.dp
                start - (start - end) * (clampedScrollValue / maxScroll)
            }
        }

        val detailsPaddingStart by remember {
            derivedStateOf {
                val start = 0.dp
                val end = 52.dp
                start - (start - end) * (clampedScrollValue / maxScroll)
            }
        }

        val detailsPaddingTop by remember {
            derivedStateOf {
                val start = 305.dp
                val end = 0.dp
                start - (start - end) * (clampedScrollValue / maxScroll)
            }
        }

        val collectionTopPadding by remember {
            derivedStateOf {
                val start = 52.5.dp
                val end = 0.dp
                start - (start - end) * (clampedScrollValue / maxScroll)
            }
        }
        val contentPadding by remember {
            derivedStateOf {
                val start = 16.dp
                val end = 0.dp
                start - (start - end) * (clampedScrollValue / maxScroll)
            }
        }
        val contentPaddingBottom by remember {
            derivedStateOf {
                val start = 0.dp
                val end = 12.dp
                start - (start - end) * (clampedScrollValue / maxScroll)
            }
        }
        val textPaddingEnd by remember {
            derivedStateOf {
                val start = 64.dp
                val end = 100.dp
                start - (start - end) * (clampedScrollValue / maxScroll)
            }
        }
        val textPaddingTop by remember {
            derivedStateOf {
                val start = 0.dp
                val end = 9.dp
                start - (start - end) * (clampedScrollValue / maxScroll)
            }
        }
        val detailsBackgroundColor by animateColorAsState(
            targetValue = if (clampedScrollValue <= 20f)
                Theme.colors.background.card
            else
                Theme.colors.background.screen,
        )
        val textFontSize by animateIntAsState(
            targetValue = if (clampedScrollValue <= 20f)
                18
            else
                16
        )
        Box(modifier = Modifier.padding(horizontal = 16.dp))
        {
            Box(
                modifier = modifier
                    .padding(start = detailsPaddingStart)
                    .padding(top = detailsPaddingTop)
                    .fillMaxWidth()
                    .background(
                        color = detailsBackgroundColor,
                        RoundedCornerShape(Theme.radius.large)
                    )
                    .padding(contentPadding)
                    .padding(bottom = contentPaddingBottom),
            ) {
                Column(
                    modifier = Modifier.padding(end = textPaddingEnd),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    AnimatedVisibility(
                        visible = clampedScrollValue <= 20f,
                        enter = slideInVertically(animationSpec = tween(1000))
                                + fadeIn(animationSpec = tween(1000)),

                        exit = slideOutVertically(animationSpec = tween(1000))
                                + fadeOut(animationSpec = tween(1000)),
                    ) {
                        Text(
                            text = type,
                            style = Theme.textStyle.label.medium.medium,
                            color = Theme.colors.brand.primary
                        )
                    }
                    Text(
                        modifier = Modifier
                            .padding(top = textPaddingTop),
                        text = title,
                        style = Theme.textStyle.title.medium.copy(fontSize = textFontSize.sp),
                        color = Theme.colors.shade.primary
                    )
                    AnimatedVisibility(
                        visible = clampedScrollValue <= 20f,
                        enter = slideInVertically(animationSpec = tween(1000))
                                + fadeIn(animationSpec = tween(1000)),

                        exit = slideOutVertically(animationSpec = tween(1000))
                                + fadeOut(animationSpec = tween(1000)),
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            horizontalAlignment = Alignment.Start,
                        ) {
                            Text(
                                text = genres,
                                style = Theme.textStyle.body.small.medium,
                                color = Theme.colors.shade.secondary
                            )
                            Row(
                                modifier = Modifier.padding(top = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (formattedRating != "0.0")
                                    InfoTextWithIcon(
                                        R.drawable.due_tone_star,
                                        formattedRating,
                                        Theme.colors.additional.primary.yellow
                                    )
                                if (duration.isNotBlank() && duration != "N/A" && duration != "null") {
                                    InfoTextWithIcon(
                                        R.drawable.due_tone_clock,
                                        duration,
                                        Theme.colors.shade.secondary
                                    )
                                }

                                if (releaseDate.isNotBlank())
                                    InfoTextWithIcon(
                                        R.drawable.due_tone_calendar,
                                        releaseDate,
                                        Theme.colors.shade.secondary
                                    )
                            }
                        }
                    }
                }
                MovieFloatingButton(
                    modifier = Modifier.align(Alignment.TopEnd),
                    buttonIcon = R.drawable.due_tone_play,
                    onClick = { onPlayClick() },
                    backgroundColor = Theme.colors.button.primary,
                    iconColor = Theme.colors.brand.tertiary,
                )
                MovieFloatingButton(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = collectionTopPadding)
                        .padding(end = collectionEndPadding),
                    buttonIcon = R.drawable.due_tone_add,
                    onClick = { onSaveClick() },
                    backgroundColor = Theme.colors.button.secondary,
                    iconColor = Theme.colors.shade.primary,
                    enabled = isSaveEnabled
                )
            }
            Box(
                modifier = Modifier
                    .padding(start = imageStartPadding)
                    .height(imageHeight)
                    .width(imageWidth)
            ) {
                SafeImageViewer(
                    imageUrl = posterUrl,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(imageRadius)),
                    isBlurEnabled = enableBlur,
                    placeholderContent = { RemoteImagePlaceholder() },
                    errorContent = { RemoteImagePlaceholder() },
                    onBlurContent = {
                        OnBlurContent()
                    }
                )
            }
        }
        AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomCenter),
            visible = clampedScrollValue > 20f,
            enter = slideInVertically(animationSpec = tween(1000))
                    + fadeIn(animationSpec = tween(1000)),

            exit = slideOutVertically(animationSpec = tween(1000))
                    + fadeOut(animationSpec = tween(1000)),
        ) {
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                thickness = 2.dp,
                color = Theme.colors.brand.tertiary
            )
        }
    }
}