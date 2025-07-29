package com.moscow.cineverse.screen.home.components

import android.graphics.BlurMaskFilter
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.zIndex
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.component.MoviePosterCard
import com.moscow.cineverse.designSystem.theme.Theme
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

@Composable
fun HomeHeaderSlider(items: List<MediaItemUiState>, modifier: Modifier = Modifier) {
    if (items.isEmpty()) return

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { items.size }
    )

    val currentItem by remember {
        derivedStateOf { items[pagerState.currentPage] }
    }

    val animatedRating by animateFloatAsState(
        targetValue = currentItem.rating,
        animationSpec = tween(durationMillis = 500),
        label = "ratingAnimation"
    )

    val pageOffset by remember {
        derivedStateOf { pagerState.currentPageOffsetFraction.absoluteValue.coerceIn(0f, 0.5f) }
    }
    val normalizedOffset = pageOffset * 2f
    val factor = (1f - normalizedOffset).coerceIn(0f, 1f)

    val shadowBlur = lerp(40.dp, 80.dp, factor)
    val shadowAlpha = lerp(0f, 0.4f, factor)
    val shadowOffset = lerp(20.dp, 25.dp, factor)



    LaunchedEffect(pagerState) {
        while (true) {
            delay(3000)
            if (!pagerState.isScrollInProgress) {
                val nextPage = (pagerState.currentPage + 1) % items.size
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

    Box(modifier = modifier.fillMaxWidth()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .doubleShadowDrop(
                    shape = RoundedCornerShape(Theme.radius.extraLarge),
                    blur = shadowBlur,
                    alpha = shadowAlpha,
                    offset = shadowOffset
                ),
            contentPadding = PaddingValues(horizontal = 32.dp),
            pageSpacing = (-40).dp,
            verticalAlignment = Alignment.CenterVertically,
            beyondViewportPageCount = 1
        ) { page ->
            val pageOffset =
                ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction)
                    .absoluteValue.coerceIn(0f, 1f)

            val animatedHeight = lerp(230.dp, 200.dp, 1f - pageOffset)
            val animatedWidth = lerp(360.dp, 312.dp, 1f - pageOffset)

            val cardAlpha = lerp(0.6f, 1f, 1f - pageOffset)
            val textAlpha = 1f - pageOffset * 3f

            Box(
                modifier = Modifier
                    .height(280.dp)
                    .zIndex(1f - pageOffset)
            ) {
                MoviePosterCard(
                    movie = items[page],
                    showRating = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(BiasAlignment(0f, pageOffset - 1f))
                        .alpha(cardAlpha)
                        .size(width = animatedWidth, height = animatedHeight)
                        .clip(RoundedCornerShape(Theme.radius.extraLarge)),
                    showBackdrop = true,
                    showTitle = false,
                )

                if (textAlpha > 0) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 32.dp)
                            .fillMaxWidth(0.8f)
                            .alpha(textAlpha),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = items[page].title,
                            color = Theme.colors.shade.primary,
                            style = Theme.textStyle.body.medium.medium,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = items[page].genres.joinToString(),
                            color = Theme.colors.shade.secondary,
                            style = Theme.textStyle.body.small.regular,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        AnimatedRatingDisplaySection(
            rating = animatedRating,
            alpha = 1f,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 4.dp, end = 32.dp)
        )
    }
}

private fun Modifier.dropShadow(
    shape: Shape,
    color: Color = Color.Black,
    blur: Dp = 80.dp,
    offsetY: Dp = 4.dp,
    offsetX: Dp = 0.dp,
    spread: Dp = 0.dp,
) = this.drawBehind {
    val shadowSize = Size(size.width + spread.toPx(), size.height + spread.toPx())
    val shadowOutline = shape.createOutline(shadowSize, layoutDirection, this)
    val paint = Paint()
    paint.color = color
    if (blur.toPx() > 0) {
        paint.asFrameworkPaint().apply {
            maskFilter = BlurMaskFilter(blur.toPx(), BlurMaskFilter.Blur.NORMAL)
        }
    }

    drawIntoCanvas { canvas ->
        canvas.save()
        canvas.translate(offsetX.toPx(), offsetY.toPx())
        canvas.drawOutline(shadowOutline, paint)
        canvas.restore()
    }
}

fun Modifier.doubleShadowDrop(
    shape: Shape,
    offset: Dp = 6.dp,
    blur: Dp = 80.dp,
    alpha: Float = 0.6f
) = this
    .dropShadow(shape, Color.Black.copy(alpha), blur = blur, offsetY = -offset)
    .dropShadow(shape, Color.White.copy(alpha), blur = blur, offsetY = offset)