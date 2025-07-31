package com.moscow.cineverse.screen.match.composable

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.util.lerp
import com.moscow.domain.model.Movie
import kotlin.math.absoluteValue

@Composable
fun MatchCarouselAnimation(
    movies: List<Movie>,
    onMovieClick: (Movie) -> Unit = {},
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { movies.size }
    )
    HorizontalPager(
        state = pagerState,
        pageSize = PageSize.Fill,
        contentPadding = PaddingValues(horizontal = 60.dp),
        modifier = Modifier,
    ) { page ->
//        MoviePosterCard(
//            movie = movies[page],
//            viewMode = ViewMode.GRID,
//            onMovieClick = { onMovieClick(it) },
//            showRating = false,
//            showGenres = false,
//            showTitle = false,
//            titleTextAlign = TextAlign.Center,
//            descriptionTextAlign = TextAlign.Center,
//            modifier = Modifier
//                .carouselLiftedCardTransition(page, pagerState)
//        )
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
        targetValue = lerp(0.9f, 0.8f, pageOffset),
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
