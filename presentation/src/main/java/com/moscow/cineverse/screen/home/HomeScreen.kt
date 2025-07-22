package com.moscow.cineverse.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.navigation.LocalNavController
import com.moscow.cineverse.screen.home.components.HomeHeader
import com.moscow.cineverse.screen.home.components.HomeHeaderSlider
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier, viewmodel: HomeViewModel = koinViewModel(),
    navController: NavHostController = LocalNavController.current,
) {
    val state by viewmodel.uiState.collectAsStateWithLifecycle()
    HomeContent(modifier, state)
}

@Composable
fun HomeContent(modifier: Modifier = Modifier, state: HomeUiState) {
    Column(modifier = modifier
        .fillMaxSize()
        .background(Theme.colors.background.screen)
        .systemBarsPadding()) {
        HomeHeader(userName = state.userName,modifier)
        Spacer(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(Theme.colors.stroke.primary),
        )

//        HomeHeaderSlider(items = state.sliderItems)


    }

}