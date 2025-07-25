package com.moscow.cineverse.screen.home.components

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.zIndex
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.designSystem.component.LoadImageWithPlaceholder
import com.moscow.cineverse.designSystem.component.RatingDisplaySection
import com.moscow.cineverse.designSystem.theme.Theme
import kotlin.math.absoluteValue

@Composable
fun HomeHeaderSlider(items: List<MediaItemUiState>, modifier: Modifier = Modifier) {
    if (items.isEmpty()) return

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { items.size }
    )
    val currentItem = remember(pagerState.currentPage) {
        items[pagerState.currentPage]
    }

    Box(modifier = modifier.fillMaxWidth()) {

        HorizontalPager(
            state = pagerState,
            modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 32.dp),
            pageSpacing = (-40).dp,
            verticalAlignment = Alignment.CenterVertically,
            beyondViewportPageCount = 2
        ) { page ->
            val pageOffset =
                ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
                    .coerceIn(0f, 1f)

            val animatedHeight = lerp(
                start = 230.dp,
                stop = 200.dp,
                fraction = 1f - pageOffset
            )
            val animatedWidth = lerp(
                start = 360.dp,
                stop = 312.dp,
                fraction = 1f - pageOffset
            )
            Box(
                modifier = Modifier
                    .height(280.dp)
                    .zIndex(1f - pageOffset)
            ) {
                LoadImageWithPlaceholder(
                    posterUrl = items[page].posterPath,
                    contentDescription = items[page].title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(BiasAlignment(0f, pageOffset - 1f))
                        .size(width = animatedWidth, height = animatedHeight)
                        .clip(RoundedCornerShape(Theme.radius.extraLarge)),
                    placeholderModifier = Modifier
                        .padding(horizontal = 4.dp)
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    placeholderCardColor = Theme.colors.shade.secondary,
                    placeholderIconSize = 32.dp
                )
            }

        }

        RatingDisplaySection(
            rating = currentItem.rating,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.dp)
                .padding(end = 24.dp)
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
                .fillMaxWidth(.6f),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = currentItem.title,
                color = Theme.colors.shade.primary,
                style = Theme.textStyle.body.medium.medium
            )
            Text(
                text = currentItem.genres.joinToString(),
                color = Theme.colors.shade.secondary,
                style = Theme.textStyle.body.small.regular
            )
        }
    }
}