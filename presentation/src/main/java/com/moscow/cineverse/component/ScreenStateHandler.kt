package com.moscow.cineverse.component

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.MovieButton
import com.moscow.cineverse.designSystem.component.MovieCircularProgressBar
import com.moscow.cineverse.designSystem.component.MovieText
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cinverse.presentation.R

@Composable
fun ScreenStateHandler(
    isLoading: Boolean,
    errorMessage: String?,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        when {
            isLoading -> {
                MovieCircularProgressBar(
                    modifier = Modifier.align(Alignment.Center),
                    gradientColors = listOf(
                        Theme.colors.brand.primary,
                        Theme.colors.brand.tertiary
                    )
                )
            }

            errorMessage != null -> {
                Log.e("ScreenStateHandler", errorMessage)
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        MovieText(
                            text = errorMessage,
                            color = Theme.colors.shade.primary
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        MovieButton(
                            buttonText = stringResource(R.string.retry),
                            textColor = Theme.colors.button.primary,
                            textStyle = Theme.textStyle.title.small,
                            onClick = onRefresh
                        )
                    }
                }
            }

            else -> {
                content.invoke()
            }
        }
    }
}