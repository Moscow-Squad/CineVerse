package com.moscow.cineverse.designSystem.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun SwipeToRemove(
    movie: Movie,
    onMovieClick: (Movie) -> Unit,
    onDeleteClick: (Movie) -> Unit,
    modifier: Modifier = Modifier,
    isVisible: Boolean = true
) {
    val density = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()
    val maxSwipeDistance = with(density) { 80.dp.toPx() }
    val offsetX = remember { Animatable(0f) }
    var showDeleteAnimation by remember { mutableStateOf(false) }
    val handleDelete = {
        showDeleteAnimation = true
        coroutineScope.launch {
            kotlinx.coroutines.delay(300)
            onDeleteClick(movie)
        }
    }

    AnimatedVisibility(
        visible = isVisible && !showDeleteAnimation,
        exit = fadeOut(tween(300)) + shrinkVertically(tween(300))
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(88.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(Theme.radius.large))
            ) {
                Box(
                    modifier = Modifier
                        .width(80.dp)
                        .fillMaxHeight()
                        .align(Alignment.CenterEnd),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(Theme.radius.large))
                            .background(Theme.colors.additional.primary.red),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.due_tone_trash),
                            contentDescription = "Delete",
                            tint = Theme.colors.button.onPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .offset { IntOffset(offsetX.value.roundToInt(), 0) }
                    .pointerInput(Unit) {
                        detectHorizontalDragGestures(
                            onDragEnd = {
                                coroutineScope.launch {
                                    if (offsetX.value <= -maxSwipeDistance / 2) {
                                        offsetX.animateTo(
                                            targetValue = -maxSwipeDistance,
                                            animationSpec = tween(200)
                                        )
                                        kotlinx.coroutines.delay(300)
                                        handleDelete()
                                    } else {
                                        offsetX.animateTo(
                                            targetValue = 0f,
                                            animationSpec = tween(200)
                                        )
                                    }
                                }
                            }
                        ) { _, dragAmount ->
                            coroutineScope.launch {
                                val newOffset =
                                    (offsetX.value + dragAmount).coerceIn(-maxSwipeDistance, 0f)
                                offsetX.snapTo(newOffset)
                            }
                        }
                    }
            ) {
                MoviePosterCard(
                    movie = movie,
                    viewMode = ViewMode.LIST,
                    onMovieClick = onMovieClick,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SwipeToRemovePreview() {
    CineVerseTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SwipeToRemove(
                movie = Movie(
                    id = 1,
                    title = "The Dark Knight",
                    posterUrl = "",
                    rating = 9.0f,
                    genres = listOf("Action", "Crime", "Drama"),
                    duration = "2h 32m",
                    releaseDate = "2008, Jul 18"
                ),
                onMovieClick = { },
                onDeleteClick = { }
            )

            SwipeToRemove(
                movie = Movie(
                    id = 2,
                    title = "Inception",
                    posterUrl = "",
                    rating = 8.8f,
                    genres = listOf("Action", "Sci-Fi", "Thriller"),
                    duration = "2h 28m",
                    releaseDate = "2010, Jul 16"
                ),
                onMovieClick = { },
                onDeleteClick = { }
            )
        }
    }
}