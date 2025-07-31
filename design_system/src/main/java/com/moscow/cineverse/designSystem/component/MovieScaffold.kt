package com.moscow.cineverse.designSystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.wrapper.MovieText
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun MovieScaffold(
    modifier: Modifier = Modifier,
    movieFloatingActionButton: @Composable () -> Unit = {},
    movieAppBar: @Composable () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    Scaffold(
        topBar = {
            Box(Modifier.statusBarsPadding()) {
                movieAppBar()
            }
        },
        floatingActionButton = {
            movieFloatingActionButton()
        },
        modifier = modifier.fillMaxSize(),
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Theme.colors.background.screen)
                    .padding(paddingValues)
            ) {
                content()
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun MovieScaffoldPreview() {
    CineVerseTheme {
        MovieScaffold(
            content = {
                Column(
                    modifier = Modifier
                ) {
                    MovieText(
                        text = "Sample Content",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            },
            movieFloatingActionButton = {
                FloatingActionButton(onClick = { }) {
                    MovieText(text = "Action")
                }
            },
            movieAppBar = {
                TopAppBar(title = { MovieText(text = "App Bar Title") })
            }
        )
    }
}