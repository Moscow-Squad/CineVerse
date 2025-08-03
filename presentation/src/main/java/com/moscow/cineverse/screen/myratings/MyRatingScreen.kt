package com.moscow.cineverse.screen.myratings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun MyRatingScreen(
    modifier: Modifier = Modifier,
    viewModel: MyRatingsViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is MyRatingsEffect.MovieClicked -> TODO()

                is MyRatingsEffect.SeriesClicked -> TODO()

                is MyRatingsEffect.NavigateBack -> {
                    navigateBack()
                }
            }
        }
    }

    MyRatingsContent()
}