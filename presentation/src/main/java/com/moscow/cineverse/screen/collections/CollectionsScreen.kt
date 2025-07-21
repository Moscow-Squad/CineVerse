package com.moscow.cineverse.screen.collections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.moscow.cineverse.designSystem.component.MovieAppBar
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cinverse.presentation.R

@Composable
fun CollectionsScreen() {
}

@Composable
private fun CollectionsContent(
    uiState: CollectionsUiState,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colors.background.screen)
    ) {
        MovieAppBar(
            title = stringResource(R.string.my_collections),
            backButtonClick = {

            }
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {

        }
    }

}