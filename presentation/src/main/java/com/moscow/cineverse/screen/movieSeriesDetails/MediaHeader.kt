package com.moscow.cineverse.screen.movieSeriesDetails

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
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
){
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp),
    ) {
        val formattedRating = try {
            val ratingValue = rating.toDouble()
            String.format("%.1f", ratingValue)
        } catch (_: NumberFormatException) {
            "0.0"
        }
        val lastScrollOffset = remember { mutableStateOf(0f) }

        val isCollapsed = remember { mutableStateOf(false) }

        val currentScroll = scrollState.firstVisibleItemScrollOffset.toFloat()
        val isScrollingDown = currentScroll > lastScrollOffset.value

        LaunchedEffect(currentScroll) {
            if (isScrollingDown && !isCollapsed.value && currentScroll > 20f) {
                isCollapsed.value = true // Collapse once
            } else if (!isScrollingDown && currentScroll == 0f) {
                isCollapsed.value = false // Expand only when at top
            }
            lastScrollOffset.value = currentScroll
        }

        val scrollProgress = remember { mutableStateOf(0f) }
        val maxScroll = 250f



        val maxAchievedScroll = remember { mutableStateOf(0f) }

        val clampedScrollValue by remember(scrollState) {
            derivedStateOf {
                val current = scrollState.firstVisibleItemScrollOffset.toFloat()
                val isScrollingDown = current > lastScrollOffset.value && current <= 250f
Log.d("dddddd", current.toString())
                if (isScrollingDown) {
                    // Scrolling down — allow animation
                    maxAchievedScroll.value = current
                } else if (current == 0f) {
                    // Only reset when we're at the very top
                    maxAchievedScroll.value = 0f
                }

                lastScrollOffset.value = current
                maxAchievedScroll.value.coerceIn(0f, 250f)
            }
        }

        LaunchedEffect(scrollState) {
            if (!isCollapsed.value) {
                // Update progress until collapsed
                scrollProgress.value = (clampedScrollValue / maxScroll).coerceIn(0f, 1f)
            } else if (currentScroll == 0f) {
                // Reset when fully expanded
                scrollProgress.value = 0f
            }
        }

        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp.dp

        val imageStartPadding by remember {
            derivedStateOf {
                val start = ( screenWidth - 216.dp - 16.dp) / 2
                val end = 0.dp
                val maxScroll = 250f
                start - (start - end) * (clampedScrollValue / maxScroll)
            }
        }

        val imageHeight by remember {
            derivedStateOf {
                val start = 289.dp
                val end = 40.dp
                val maxScroll = 250f
                start - (start - end) * (clampedScrollValue / maxScroll)
            }
        }
        val imageWidth by remember {
            derivedStateOf {
                val start = 216.dp
                val end = 40.dp
                val maxScroll = 250f
                start - (start - end) * (clampedScrollValue / maxScroll)
            }
        }

        val collectionEndPadding by remember {
            derivedStateOf {
                val start = 0.dp
                val end = 48.dp
                val maxScroll = 250f
                start - (start - end) * (clampedScrollValue / maxScroll)
            }
        }

        val detailsPaddingStart by remember {
            derivedStateOf {
                val start = 0.dp
                val end = 52.dp
                val maxScroll = 250f
                start - (start - end) * (clampedScrollValue / maxScroll)
            }
        }

        val detailsPaddingTop by remember {
            derivedStateOf {
                val start = 305.dp
                val end = 0.dp
                val maxScroll = 250f
                start - (start - end) * (clampedScrollValue / maxScroll)
            }
        }

        val collectionTopPadding by remember {
            derivedStateOf {
                val start = 68.5.dp
                val end = 0.dp
                val maxScroll = 250f
                start - (start - end) * (clampedScrollValue / maxScroll)
            }
        }
        val contentPadding by remember {
            derivedStateOf {
                val start = 16.dp
                val end = 0.dp
                val maxScroll = 250f
                start - (start - end) * (clampedScrollValue / maxScroll)
            }
        }
        val contentPaddingBottom by remember {
            derivedStateOf {
                val start = 0.dp
                val end = 12.dp
                val maxScroll = 250f
                start - (start - end) * (clampedScrollValue / maxScroll)
            }
        }
        val detailsBackgroundColor by animateColorAsState(
            targetValue = if (clampedScrollValue <= 20f)
                Theme.colors.background.card
            else
                Theme.colors.background.screen,
        )
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
                    .clip(RoundedCornerShape(Theme.radius.extraLarge)),
                isBlurEnabled = enableBlur,
                placeholderContent = { RemoteImagePlaceholder() },
                errorContent = { RemoteImagePlaceholder() },
                onBlurContent = {
                    OnBlurContent()
                }
            )
        }
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
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.Start,
            ) {
                AnimatedVisibility(
                    visible = !isCollapsed.value,
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
                    text = title,
                    style = Theme.textStyle.title.medium,
                    color = Theme.colors.shade.primary
                )
                AnimatedVisibility(
                    visible = !isCollapsed.value,
                    enter = slideInVertically(animationSpec = tween(1000))
                            + fadeIn(animationSpec = tween(1000)),

                    exit = slideOutVertically(animationSpec = tween(1000))
                            + fadeOut(animationSpec = tween(1000)),
                ){
                    Column (
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
            )
        }
    }
}