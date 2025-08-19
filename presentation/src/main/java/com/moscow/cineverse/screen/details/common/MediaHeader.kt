package com.moscow.cineverse.screen.details.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moscow.cineverse.common_ui_state.DurationUiState
import com.moscow.cineverse.designSystem.component.blur.OnBlurContent
import com.moscow.cineverse.designSystem.component.blur.RemoteImagePlaceholder
import com.moscow.cineverse.designSystem.component.button.MovieFloatingButton
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.image_viewer.component.SafeImageViewer
import com.moscow.cineverse.mapper.formatDate
import com.moscow.cineverse.mapper.formatDuration
import com.moscow.cinverse.presentation.R
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

@Composable
fun MediaHeader(
    modifier: Modifier = Modifier,
    scrollState: LazyListState,
    title: String,
    genres: String,
    rating: String,
    duration: DurationUiState,
    releaseDate: LocalDate?,
    posterUrl: String,
    type: String,
    enableBlur: String,
    onSaveClick: (() -> Unit)? = null,
    onPlayClick: () -> Unit = {},
    isSaveEnabled: Boolean = true
) {
    val maxScroll = 400f
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val coroutineScope = rememberCoroutineScope()

    val absoluteScroll = remember(scrollState) {
        derivedStateOf {
            scrollState.firstVisibleItemIndex * 1000 + scrollState.firstVisibleItemScrollOffset
        }
    }
    val isScrolling = remember { mutableStateOf(false) }
    val lastScrollValue = remember { mutableFloatStateOf(0f) }
    val isScrollingUp = remember { derivedStateOf { absoluteScroll.value < lastScrollValue.floatValue } }

    val imageStartPadding = remember { Animatable(((screenWidth - 216 - 16) / 2).toFloat()) }
    val imageRadius = remember { Animatable(16f) }
    val imageHeight = remember { Animatable(289f) }
    val imageWidth = remember { Animatable(216f) }
    val collectionEndPadding = remember { Animatable(0f) }
    val detailsPaddingStart = remember { Animatable(0f) }
    val detailsPaddingTop = remember { Animatable(305f) }
    val collectionTopPadding = remember { Animatable(52.5f) }
    val contentPadding = remember { Animatable(16f) }
    val contentPaddingBottom = remember { Animatable(0f) }
    val textPaddingEnd = remember { Animatable(64f) }
    val textPaddingTop = remember { Animatable(0f) }
    val isAllDetailsvisible = remember { Animatable(1f) }
    val isScrolledVisible = remember { Animatable(0f) }

    LaunchedEffect(absoluteScroll.value) {
        isScrolling.value = true
        lastScrollValue.floatValue = absoluteScroll.value.toFloat()
        kotlinx.coroutines.delay(150)
        isScrolling.value = false
    }

    LaunchedEffect(isScrolling.value, absoluteScroll.value) {
        if (!isScrolling.value) {
            val scrollValue = absoluteScroll.value.toFloat().coerceIn(0f, maxScroll)
            val targetFraction = if (scrollValue < maxScroll / 2 || isScrollingUp.value) 0f else 1f
            coroutineScope.launch {
                launch { imageStartPadding.animateTo(
                    targetValue = if (targetFraction == 0f) (screenWidth.dp - 216.dp - 16.dp).value / 2 else 0f,
                    animationSpec = tween(300)
                ) }
                launch { imageRadius.animateTo(
                    targetValue = if (targetFraction == 0f) 16f else 100f,
                    animationSpec = tween(300)
                ) }
                launch { imageHeight.animateTo(
                    targetValue = if (targetFraction == 0f) 289f else 40f,
                    animationSpec = tween(300)
                ) }
                launch { imageWidth.animateTo(
                    targetValue = if (targetFraction == 0f) 216f else 40f,
                    animationSpec = tween(300)
                ) }
                launch { collectionEndPadding.animateTo(
                    targetValue = if (targetFraction == 0f) 0f else 48f,
                    animationSpec = tween(300)
                ) }
                launch { detailsPaddingStart.animateTo(
                    targetValue = if (targetFraction == 0f) 0f else 52f,
                    animationSpec = tween(300)
                ) }
                launch { detailsPaddingTop.animateTo(
                    targetValue = if (targetFraction == 0f) 305f else 0f,
                    animationSpec = tween(300)
                ) }
                launch { collectionTopPadding.animateTo(
                    targetValue = if (targetFraction == 0f) 52.5f else 0f,
                    animationSpec = tween(300)
                ) }
                launch { contentPadding.animateTo(
                    targetValue = if (targetFraction == 0f) 16f else 0f,
                    animationSpec = tween(300)
                ) }
                launch { contentPaddingBottom.animateTo(
                    targetValue = if (targetFraction == 0f) 0f else 12f,
                    animationSpec = tween(300)
                ) }
                launch { textPaddingEnd.animateTo(
                    targetValue = if (targetFraction == 0f) 64f else 100f,
                    animationSpec = tween(300)
                ) }
                launch { textPaddingTop.animateTo(
                    targetValue = if (targetFraction == 0f) 0f else 9f,
                    animationSpec = tween(300)
                ) }
                launch { isAllDetailsvisible.animateTo(
                    targetValue = if (targetFraction == 0f) 1f else 0f,
                    animationSpec = tween(300)
                ) }
                launch { isScrolledVisible.animateTo(
                    targetValue = if (targetFraction == 0f) 0f else 1f,
                    animationSpec = tween(300)
                ) }
            }
        } else {
            val scrollFraction = absoluteScroll.value.toFloat().coerceIn(0f, maxScroll) / maxScroll
            imageStartPadding.snapTo(
                ((screenWidth.dp - 216.dp - 16.dp) / 2).value - (((screenWidth.dp - 216.dp - 16.dp) / 2).value - 0f) * scrollFraction
            )
            imageRadius.snapTo(16f - (16f - 100f) * scrollFraction)
            imageHeight.snapTo(289f - (289f - 40f) * scrollFraction)
            imageWidth.snapTo(216f - (216f - 40f) * scrollFraction)
            collectionEndPadding.snapTo(0f - (0f - 48f) * scrollFraction)
            detailsPaddingStart.snapTo(0f - (0f - 52f) * scrollFraction)
            detailsPaddingTop.snapTo(305f - (305f - 0f) * scrollFraction)
            collectionTopPadding.snapTo(52.5f - (52.5f - 0f) * scrollFraction)
            contentPadding.snapTo(16f - (16f - 0f) * scrollFraction)
            contentPaddingBottom.snapTo(0f - (0f - 12f) * scrollFraction)
            textPaddingEnd.snapTo(64f - (64f - 100f) * scrollFraction)
            textPaddingTop.snapTo(0f - (0f - 9f) * scrollFraction)
            isAllDetailsvisible.snapTo(1f - scrollFraction)
            isScrolledVisible.snapTo(scrollFraction)
        }
    }

    val detailsBackgroundColor by animateColorAsState(
        targetValue = if (isAllDetailsvisible.value > 0.1f) Theme.colors.background.card else Theme.colors.background.screen,
        animationSpec = tween(300)
    )
    val textFontSize by animateIntAsState(
        targetValue = if (isAllDetailsvisible.value > 0.1f) 18 else 16,
        animationSpec = tween(300)
    )
    Box(
        modifier = Modifier,
    ) {
        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
            Box(
                modifier = modifier
                    .padding(start = detailsPaddingStart.value.dp)
                    .padding(top = detailsPaddingTop.value.dp)
                    .fillMaxWidth()
                    .background(
                        color = detailsBackgroundColor,
                        RoundedCornerShape(Theme.radius.large)
                    )
                    .padding(contentPadding.value.dp)
                    .padding(bottom = contentPaddingBottom.value.dp),
            ) {
                Column(
                    modifier = Modifier.padding(end = textPaddingEnd.value.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    AnimatedVisibility(
                        visible = isAllDetailsvisible.value > 0.1f,
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
                            .padding(top = textPaddingTop.value.dp),
                        text = title,
                        style = Theme.textStyle.title.medium.copy(fontSize = textFontSize.sp),
                        color = Theme.colors.shade.primary
                    )
                    AnimatedVisibility(
                        visible = isAllDetailsvisible.value > 0.1f,
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
                                if (rating != "0.0")
                                    InfoTextWithIcon(
                                        R.drawable.due_tone_star,
                                        rating,
                                        Theme.colors.additional.primary.yellow
                                    )
                                if (duration != DurationUiState(0,0))
                                    InfoTextWithIcon(
                                        R.drawable.due_tone_clock,
                                        duration.formatDuration(),
                                        Theme.colors.shade.secondary
                                    )

                                releaseDate?.let { date ->
                                    InfoTextWithIcon(
                                        R.drawable.due_tone_calendar,
                                        date.formatDate(),
                                        Theme.colors.shade.secondary
                                    )
                                }
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
                        .padding(top = collectionTopPadding.value.dp)
                        .padding(end = collectionEndPadding.value.dp),
                    buttonIcon = R.drawable.due_tone_add,
                    onClick = {
                        onSaveClick?.invoke()
                    },
                    backgroundColor = Theme.colors.button.secondary,
                    iconColor = Theme.colors.shade.primary,
                    enabled = onSaveClick != null && isSaveEnabled
                )
            }
            Box(
                modifier = Modifier
                    .padding(start = imageStartPadding.value.dp)
                    .height(imageHeight.value.dp)
                    .width(imageWidth.value.dp)
            ) {
                SafeImageViewer(
                    imageUrl = posterUrl,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(imageRadius.value.dp)),
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
            visible = isScrolledVisible.value > 0.1f,
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