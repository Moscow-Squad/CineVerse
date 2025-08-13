package com.moscow.cineverse.screen.match.composable

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.zIndex
import com.moscow.cineverse.designSystem.component.blur.OnBlurContent
import com.moscow.cineverse.designSystem.component.blur.RemoteImagePlaceholder
import com.moscow.cineverse.designSystem.component.button.MovieButton
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.image_viewer.component.SafeImageViewer
import com.moscow.cineverse.mapper.toFormattedReleasedDate
import com.moscow.cineverse.mapper.toHourMinuteFormat
import com.moscow.cineverse.screen.movieSeriesDetails.DetailCard
import com.moscow.cineverse.screen.movie_details.MovieScreenState
import com.moscow.cinverse.presentation.R
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

@Composable
fun MatchCarouselAnimation(
    movies: List<MovieScreenState.MovieDetailsUiState>,
    onMovieClick: (Int) -> Unit,
    onSaveClick: (Int) -> Unit,
    onPlayClick: (String) -> Unit,
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

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
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
                            .offset(
                                x = when {
                                    page < pagerState.currentPage -> 40.dp * pageOffset
                                    page > pagerState.currentPage -> ((-40).dp) * pageOffset
                                    else -> 0.dp
                                }
                            )
                        ,
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

        Spacer(modifier = Modifier.height(16.dp))

        DetailCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .animateContentSize(
                    animationSpec = spring(
                        stiffness = Spring.StiffnessMediumLow,
                        dampingRatio = Spring.DampingRatioLowBouncy
                    )
                ),
            title = movies[pagerState.currentPage].title,
            genres = movies[pagerState.currentPage].genres.joinToString(", "),
            rating = movies[pagerState.currentPage].rating.toString(),
            duration = movies[pagerState.currentPage].duration.toHourMinuteFormat(LocalContext.current),
            releaseDate = movies[pagerState.currentPage].releaseDate?.toFormattedReleasedDate()
                ?: "",
            type = stringResource(R.string.movie),
            onSaveClick = { onSaveClick(movies[pagerState.currentPage].id) },
            onPlayClick = { onPlayClick(movies[pagerState.currentPage].trailerPath) },
        )

        Spacer(modifier = Modifier.height(32.dp))

        MovieButton(
            buttonText = stringResource(R.string.view_details),
            textColor = Theme.colors.button.onPrimary,
            textStyle = Theme.textStyle.body.medium.medium,
            onClick = {
                onMovieClick(
                    movies[pagerState.currentPage].id
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            buttonColor = Theme.colors.button.primary,
            cornerRadius = Theme.radius.large
        )
    }
}
