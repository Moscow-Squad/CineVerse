package com.moscow.cineverse.screen.home.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.zIndex
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.component.MediaPosterCard
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.utlis.noRibbleClick
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeHeaderSlider(
    items: List<MediaItemUiState>,
    onSliderClick:(MediaItemUiState)->Unit,
    enableBlur: String,
    modifier: Modifier = Modifier
) {
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
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val scope = rememberCoroutineScope()

    val screenWidth = with(density) { configuration.screenWidthDp.dp }
    val width = screenWidth - 48.dp

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
                .fillMaxWidth(),
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
                    .height(270.dp)
                    .zIndex(1f - pageOffset)
            ) {
                MediaPosterCard(
                    mediaItem = items[page],
                    showRating = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(BiasAlignment(0f, pageOffset - 1f))
                        .alpha(cardAlpha)
                        .size(width = animatedWidth, height = animatedHeight)
                        .clip(RoundedCornerShape(Theme.radius.extraLarge)),
                    onMediaItemClick = { onSliderClick(items[page]) },
                    showBackdrop = true,
                    showTitle = false,
                    enableBlur = enableBlur,
                    useFixedHeight = true
                )
                if (textAlpha > 0) {
                    AnimatedRatingDisplaySection(
                        rating = animatedRating,
                        alpha = 1f,
                        modifier = Modifier
                            .align(Alignment.TopEnd)

                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 32.dp)
                .padding(bottom = 32.dp, top = 8.dp)
                .fillMaxWidth(0.7f)
                ,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = currentItem.title,
                color = Theme.colors.shade.primary,
                style = Theme.textStyle.body.medium.medium,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,

                maxLines = 1,
                modifier = Modifier.noRibbleClick{ onSliderClick(currentItem) }
            )
            Text(
                text = currentItem.genres.joinToString(),
                color = Theme.colors.shade.secondary,
                style = Theme.textStyle.body.small.regular,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }

    }
}
