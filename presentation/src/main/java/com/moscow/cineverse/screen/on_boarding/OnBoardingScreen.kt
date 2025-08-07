package com.moscow.cineverse.screen.on_boarding

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.screen.on_boarding.components.BottomOnBoardingCard
import com.moscow.cineverse.screen.on_boarding.components.OnBoardingPage

@Composable
fun OnBoardingScreen(
    viewModel: OnBoardingViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit,
) {
    val state by viewModel.uiState.collectAsState()
    val pagerState = rememberPagerState(
        initialPage = state.currentPage,
        pageCount = { state.pages.size }
    )
    LaunchedEffect(viewModel) {
        viewModel.uiEffect.collect { event ->
            when (event) {
                is OnBoardingScreenEvents.NavigateToLoginScreen -> {
                    navigateToLogin()
                }
            }
        }
    }
    LaunchedEffect(state.currentPage) {
        if (pagerState.currentPage != state.currentPage) {
            pagerState.animateScrollToPage(
                state.currentPage,
                animationSpec = tween(easing = EaseInOut)
            )
        }
    }
    OnBoardingScreenContent(pagerState = pagerState, state = state, interactionListener = viewModel)
}

@Composable
fun OnBoardingScreenContent(
    pagerState: PagerState,
    state: OnBoardingState,
    interactionListener: OnBoardingInteractionListener
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Theme.colors.background.screen),
            pageSpacing = (-20).dp,
            beyondViewportPageCount = 1,
        ) { pageIndex ->
            val layoutDirection = LocalLayoutDirection.current
            val isRtl = layoutDirection == LayoutDirection.Rtl

            val offset = (pageIndex - pagerState.currentPage) + pagerState.currentPageOffsetFraction

            val rotationDegrees by remember(offset, isRtl) {
                derivedStateOf {
                    val base = if (offset != 0f) offset * 18f else 0f
                    if (isRtl) -base else base
                }
            }

            val targetRadius = if (rotationDegrees != 0f) 24.dp else 0.dp

            val animatedCornerRadius by animateDpAsState(
                targetValue = targetRadius,
                animationSpec = tween(
                    durationMillis = 500,
                    easing = LinearOutSlowInEasing
                ),
                label = "cornerRadius"
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .rotate(rotationDegrees)
                        .clip(RoundedCornerShape(animatedCornerRadius))
                ) {
                    OnBoardingPage(
                        pageUiState = state.pages[pageIndex]
                    )
                }
            }
        }
        BottomOnBoardingCard(
            state = state,
            onClickNextButton = interactionListener::onClickNextButton,
            onClickPreviousButton = interactionListener::onClickPreviousButton,
            onClickGetStartedButton = interactionListener::onClickGetStartedButton
        )
    }
}

@Preview()
@Composable
fun OnBoardingScreenPreview() {
    OnBoardingScreen(navigateToLogin = {})
}