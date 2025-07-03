package com.moscow.cineverse.designSystem.component.home

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.util.lerp
import com.moscow.cineverse.designSystem.component.Movie
import com.moscow.cineverse.designSystem.component.MoviePosterCard
import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

@Composable
fun CarouselAnimation(
    movies: List<Movie>,
    autoScrollDuration: Long = 3000L,
    onMovieClick: (Movie) -> Unit = {},
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { movies.size }
    )

    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

    var currentPageKey by remember { mutableStateOf(0) }

    if (!isDragged) {
        LaunchedEffect(key1 = currentPageKey) {
            delay(autoScrollDuration)
            val nextPage = (pagerState.currentPage + 1) % movies.size
            pagerState.animateScrollToPage(nextPage)
            currentPageKey = nextPage
        }
    }
    HorizontalPager(
        state = pagerState,
        pageSize = PageSize.Fill,
        contentPadding = PaddingValues(horizontal = 60.dp),
        modifier = Modifier,
    ) { page ->
        MoviePosterCard(
            movie = movies[page],
            viewMode = ViewMode.GRID,
            onMovieClick = { onMovieClick(it) },
            showRating = true,
            showGenres = true,
            titleTextAlign = TextAlign.Center,
            descriptionTextAlign = TextAlign.Center,
            modifier = Modifier.carouselLiftedCardTransition(page, pagerState)
        )
    }
}

@Composable
fun Modifier.carouselLiftedCardTransition(
    page: Int,
    pagerState: PagerState,
): Modifier = composed {
    val pageOffset = ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction)
        .absoluteValue
        .coerceIn(0f, 1f)

    val animatedScaleX by animateFloatAsState(
        targetValue = lerp(1.2f, 0.9f, pageOffset),
        animationSpec = tween(300)
    )
    val animatedScaleY by animateFloatAsState(
        targetValue = lerp(1f, 1.15f, pageOffset),
        animationSpec = tween(300)
    )
    val animatedAlpha by animateFloatAsState(
        targetValue = lerp(1f, 0.3f, pageOffset),
        animationSpec = tween(300)
    )
    val animatedYOffset by animateDpAsState(
        targetValue = lerp(0.dp, 40.dp, pageOffset),
        animationSpec = tween(300)
    )
    this
        .graphicsLayer {
            transformOrigin = TransformOrigin(0.5f, 0f)
            scaleX = animatedScaleX
            scaleY = animatedScaleY
            alpha = animatedAlpha
        }
        .offset {
            IntOffset(0, animatedYOffset.roundToPx())
        }
}

@Preview(showBackground = true)
@Composable
private fun AwesomeCarouselPreview() {

    CineVerseTheme {
        Column(
            modifier = Modifier.background(Color.Black)
        ) {
            val sampleMovies = List(5) {
                Movie(
                    id = it,
                    title = "Inception $it",
                    posterUrl = "",
                    rating = 8.8f,
                    genres = listOf("Action", "Sci-Fi"),
                    duration = "2h 28m",
                    releaseDate = "2010, Jul 16"
                )
            }
            CarouselAnimation(movies = sampleMovies)
        }
    }
}



