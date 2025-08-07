package com.moscow.cineverse.screen.cast_details.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.component.NoInternetScreen
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cinverse.presentation.R
import com.moscow.cinverse.presentation.R as PresentationR

@Composable
fun LoadingContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = Theme.colors.brand.primary
        )
    }
}

@Composable
fun ErrorContent(
    errorMessage: Int,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (errorMessage == R.string.no_internet_connection){
            NoInternetScreen(onRetry = onRetry)
        }
        else{
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(errorMessage),
                    color = Theme.colors.shade.primary,
                    style = Theme.textStyle.body.medium.regular,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomButton(
                    text = stringResource(PresentationR.string.retry),
                    onClick = onRetry
                )
            }
        }
    }
}

@Composable
fun EmptyContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(PresentationR.string.no_content_available),
            color = Theme.colors.shade.primary,
            style = Theme.textStyle.body.medium.regular
        )
    }
}

@Composable
private fun CustomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(Theme.radius.large))
            .background(Theme.colors.brand.primary)
            .clickable { onClick() }
            .padding(horizontal = 24.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            style = Theme.textStyle.body.medium.regular
        )
    }
}