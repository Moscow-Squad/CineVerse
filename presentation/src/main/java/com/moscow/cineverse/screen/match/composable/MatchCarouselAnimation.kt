package com.moscow.cineverse.screen.match.composable

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.zIndex
import com.moscow.cineverse.component.EmptyState
import com.moscow.cineverse.component.MediaPosterCard
import com.moscow.cineverse.designSystem.component.button.MovieButton
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.mapper.formatDate
import com.moscow.cineverse.screen.details.common.DetailCard
import com.moscow.cineverse.screen.details.movie_details.MovieScreenState
import com.moscow.cineverse.screen.details.movie_details.toMediaItem
import com.moscow.cineverse.screen.home.components.AnimatedRatingDisplaySection
import com.moscow.cinverse.presentation.R
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MatchCarouselAnimation(
    movies: List<MovieScreenState.MovieDetailsUiState>,
    isBlurEnabled: String,
    onMovieClick: (Int) -> Unit,
    onSaveClick: (Int) -> Unit,
    onPlayClick: (Int, String) -> Unit,
    modifier: Modifier = Modifier
) {

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { movies.size }
    )

    val currentItem by remember {
        derivedStateOf { movies[pagerState.currentPage] }
    }

    val animatedRating by animateFloatAsState(
        targetValue = currentItem.rating.toFloat(),
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
            if (!pagerState.isScrollInProgress && movies.isNotEmpty()) {
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

    if (movies.isEmpty()) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            EmptyState(
                icon = painterResource(R.drawable.ic_search),
                title = stringResource(R.string.nothing_found),
                description = stringResource(R.string.we_searched_the_entire_universe_but_found_nothing_matching_your_query_try_something_else)
            )
        }
    } else {
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
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 32.dp),
                    pageSpacing = (-20).dp,
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
                            mediaItem = movies[page].toMediaItem(),
                            showRating = false,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(BiasAlignment(0f, pageOffset - 1f))
                                .alpha(cardAlpha)
                                .size(width = animatedWidth, height = animatedHeight)
                                .clip(RoundedCornerShape(Theme.radius.extraLarge)),
                            onMediaItemClick = { },
                            showBackdrop = true,
                            showTitle = false,
                            enableBlur = isBlurEnabled,
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
                titleOverFlow = true,
                genres = movies[pagerState.currentPage].genres.joinToString(", "),
                rating = movies[pagerState.currentPage].rating,
                duration = if (movies[pagerState.currentPage].duration.hours == 0 && movies[pagerState.currentPage].duration.minutes == 0) "null" else movies[pagerState.currentPage].duration.toString(),
                releaseDate = movies[pagerState.currentPage].releaseDate?.formatDate(context = LocalContext.current)
                    ?: "",
                type = stringResource(R.string.movie),
                onSaveClick = { onSaveClick(movies[pagerState.currentPage].id) },
                onPlayClick = {
                    onPlayClick(
                        movies[pagerState.currentPage].id,
                        movies[pagerState.currentPage].trailerUrl
                    )
                },
            )

            Spacer(modifier = Modifier.height(32.dp))

            MovieButton(
                buttonText = stringResource(R.string.view_details),
                textColor = Theme.colors.button.onPrimary,
                textStyle = Theme.textStyle.body.medium.regular,
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
}