package com.moscow.cineverse.screen.on_boarding.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.button.MovieExtendedFloatingButton
import com.moscow.cineverse.designSystem.component.button.MovieFloatingButton
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.screen.on_boarding.OnBoardingState
import com.moscow.cinverse.presentation.R

@Composable
fun BottomOnBoardingCard(
    state: OnBoardingState,
    onClickPreviousButton: () -> Unit,
    onClickNextButton: () -> Unit,
    onClickGetStartedButton: () -> Unit
) {
    val context = LocalContext.current
    val layoutDirection = LocalLayoutDirection.current
    val isRtl = layoutDirection == LayoutDirection.Rtl
    val previousPage = remember { mutableIntStateOf(state.currentPage) }
    val slideDirection = remember(state.currentPage) {
        val isForward = state.currentPage > previousPage.intValue
        previousPage.intValue = state.currentPage

        when {
            isForward && isRtl -> -1
            !isForward && isRtl -> 1
            isForward -> 1
            else -> -1
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = Theme.colors.background.bottomSheet,
                    shape = RoundedCornerShape(Theme.radius.extraLarge)
                )
                .fillMaxWidth()

                .padding(
                    top = 32.dp, start = 24.dp,
                    end = 24.dp, bottom = 24.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedContent(
                targetState = state.pages[state.currentPage].title,
                transitionSpec = {
                    slideInHorizontally { fullWidth -> fullWidth * slideDirection } togetherWith
                            slideOutHorizontally { fullWidth -> -fullWidth * slideDirection }
                },
                label = "TitleSlideAnimation"
            ) { title ->
                Text(
                    text = title.asString(context),
                    style = Theme.textStyle.title.medium,
                    color = Theme.colors.shade.primary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            AnimatedContent(
                targetState = state.pages[state.currentPage].description,
                transitionSpec = {
                    slideInHorizontally { fullWidth -> fullWidth * slideDirection } togetherWith
                            slideOutHorizontally { fullWidth -> -fullWidth * slideDirection }
                },
                label = "DescriptionSlideAnimation"
            ) { description ->
                Text(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    text = description.asString(context),
                    style = Theme.textStyle.body.medium.medium,
                    color = Theme.colors.shade.secondary,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AnimatedVisibility(state.currentPage != 0) {
                    MovieFloatingButton(
                        modifier = Modifier.size(48.dp),
                        buttonIcon = R.drawable.outline_alt_arrow_left,
                        onClick = onClickPreviousButton,
                        backgroundColor = Theme.colors.button.secondary,
                        iconColor = Theme.colors.shade.primary
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                AnimatedContent(
                    targetState = state.currentPage == state.pages.lastIndex,
                    label = "ButtonAnimation",
                    transitionSpec = {
                        fadeIn() togetherWith (fadeOut())
                    }
                ) { isLastPage ->
                    if (!isLastPage) {
                        MovieFloatingButton(
                            modifier = Modifier.size(48.dp),
                            buttonIcon = R.drawable.outline_alt_arrow_right,
                            onClick = onClickNextButton,
                            backgroundColor = Theme.colors.button.primary,
                            iconColor = Theme.colors.background.screen
                        )
                    } else {
                        MovieExtendedFloatingButton(
                            onClick = onClickGetStartedButton,
                            icon = painterResource(R.drawable.outline_alt_arrow_right,),
                            buttonText = stringResource(R.string.get_started),
                            backgroundColor = Theme.colors.button.primary,
                            contentPadding = PaddingValues(
                                top = 14.dp,
                                bottom = 14.dp,
                                start = 24.dp,
                                end = 14.dp
                            ),
                            iconColor = Theme.colors.button.onPrimary,
                        )
                    }
                }
            }
        }
    }
}