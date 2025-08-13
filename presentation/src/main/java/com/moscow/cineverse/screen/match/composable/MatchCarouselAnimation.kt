package com.moscow.cineverse.screen.match.composable

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.zIndex
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.designSystem.component.blur.OnBlurContent
import com.moscow.cineverse.designSystem.component.blur.RemoteImagePlaceholder
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.image_viewer.component.SafeImageViewer
import com.moscow.domain.model.MediaType
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

@Composable
fun MatchCarouselAnimation(
    movies: List<MediaItemUiState>,
    onMovieClick: (Int, MediaType) -> Unit,
    modifier: Modifier = Modifier
) {
    if (movies.isEmpty()) return

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { movies.size }
    )

    LaunchedEffect(pagerState) {
        while (true) {
            delay(3000)
            if (!pagerState.isScrollInProgress) {
                val nextPage = (pagerState.currentPage + 1) % movies.size
                pagerState.animateScrollToPage(
                    nextPage,
                    animationSpec = spring(
                        stiffness = Spring.StiffnessVeryLow,
                        dampingRatio = Spring.DampingRatioLowBouncy
                    )
                )
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(350.dp),
        contentAlignment = Alignment.Center
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 60.dp),
            pageSpacing = (-80).dp,
            verticalAlignment = Alignment.CenterVertically,
            beyondViewportPageCount = 2
        ) { page ->
            val pageOffset =
                ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction)
                    .absoluteValue.coerceIn(0f, 1f)

            val animatedHeight = lerp(320.dp, 267.dp, pageOffset)
            val animatedWidth = lerp(240.dp, 200.dp, pageOffset)

            val cardAlpha = lerp(1f, 0.6f, pageOffset)
            val scale = lerp(1f, 0.85f, pageOffset)

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(1f - pageOffset)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    },
                contentAlignment = Alignment.Center
            ) {
                SafeImageViewer(
                    imageUrl = movies[page].posterPath,
                    modifier = Modifier
                        .alpha(cardAlpha)
                        .size(width = animatedWidth, height = animatedHeight)
                        .clip(RoundedCornerShape(Theme.radius.extraLarge))
                        .clickable { onMovieClick(movies[page].id, movies[page].mediaType) },
                    isBlurEnabled = "high",
                    placeholderContent = { RemoteImagePlaceholder() },
                    errorContent = { RemoteImagePlaceholder() },
                    onBlurContent = {
                        OnBlurContent()
                    }
                )
            }
        }
    }
}
